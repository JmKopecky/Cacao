package dev.prognitio.cacao;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class CourseImportUtil {


    public static ArrayList<Course> parseImportedDocuments(ArrayList<Document> retrievedDocs) throws Exception {
        ArrayList<Course> output = new ArrayList<>();

        ArrayList<String> RPClassStrings = new ArrayList<>();
        ArrayList<String> CSClassStrings = new ArrayList<>();


        //parse documents into course strings
        Elements targetRPElems = retrievedDocs.get(0).select(".borderLeftGrading");
        for (Element e:targetRPElems) {
            RPClassStrings.add(e.attr("cellKey"));
        }

        Elements targetCCElems = retrievedDocs.get(1).select("table.ssTable");
        if (!targetCCElems.isEmpty()) {
            targetCCElems.remove(0);
        }
        targetCCElems = targetCCElems.select("tr").select("[valign=\"top\"]");
        for (Element e:targetCCElems) {
            Elements tdContainer = e.select("td");
            StringBuilder classString = new StringBuilder();
            for (Element f:tdContainer) {
                classString.append(",").append(f.text());
            }
            classString = new StringBuilder(classString.substring(1));
            CSClassStrings.add(classString.toString());
        }


        if (RPClassStrings.isEmpty() || CSClassStrings.isEmpty()) {
            Logger.log("One or more of the lists of course strings was null.", LogType.ERROR, null);
            throw new Exception();
        }


        //parse report card courses

        HashMap<String, String> RPClassStringsAsMap = new HashMap<>();

        //rp card: sectionIndex=DC14,gradeTypeIndex=1st Semester Exam,courseIndex=EDUC 1200,calendarIndex=1,gradeIndex=100,
        //teacherIndex=Jones^ Hollie,dayCodeIndex=T - 8,locIndex=015

        for (String rpString : RPClassStrings) {
            String courseName = rpString.contains("courseIndex=") ? rpString.split("courseIndex=")[1].split(",")[0] : "null";

            if (courseDataReference.containsKey(courseName)) {
                courseName = Objects.requireNonNull(courseDataReference.get(courseName))[0];
            }

            String gradeType = rpString.contains("gradeTypeIndex=") ? rpString.split("gradeTypeIndex=")[1].split(",")[0] : "null";
            String teacher = rpString.contains("teacherIndex=") ? rpString.split("teacherIndex=")[1].split(",")[0] : "null";
            String grade = rpString.contains("gradeIndex=") ? rpString.split("gradeIndex=")[1].split(",")[0] : "null";
            String sectionIndex = rpString.contains("sectionIndex=") ? rpString.split("sectionIndex=")[1].split(",")[0] : "null";

            String semester = switch (gradeType) {
                case "1st 9 Weeks", "2nd 9 Weeks", "1st Semester Exam", "1st Semester Avg" ->
                        "fall";
                case "3rd 9 Weeks", "4th 9 Weeks", "2nd Semester Exam", "2nd Semester Avg" ->
                        "spring";
                default -> "null";
            };

            courseName += "&semester=" + semester;


            switch (gradeType) {
                case "1st 9 Weeks", "3rd 9 Weeks": gradeType = "report1"; break;
                case "2nd 9 Weeks", "4th 9 Weeks": gradeType = "report2"; break;
                case "1st Semester Exam", "2nd Semester Exam": gradeType = "exam"; break;
                case "1st Semester Avg", "2nd Semester Avg": gradeType = "final"; break;
                default: continue;
            }


            String courseString = "name=" + courseName + "|teacher=" + teacher + "|sectionIndex=" + sectionIndex;

            if (RPClassStringsAsMap.containsKey(courseName)) {
                String prevCourseString = RPClassStringsAsMap.get(courseName);
                String gradePortion = prevCourseString.split("\\|grade:")[1];

                String rp1 = gradePortion.split("report1=")[1].split(",")[0];
                String rp2 = gradePortion.split("report2=")[1].split(",")[0];
                String exam = gradePortion.split("exam=")[1].split(",")[0];
                String finalgrade = gradePortion.split("final=")[1];

                String newGradePortion = "|grade:" +
                        "report1=" + (gradeType.equals("report1") ? grade : rp1) +
                        ",report2=" + (gradeType.equals("report2") ? grade : rp2) +
                        ",exam=" + (gradeType.equals("exam") ? grade : exam) +
                        ",final=" + (gradeType.equals("final") ? grade : finalgrade);

                RPClassStringsAsMap.put(courseName, prevCourseString.split("\\|grade:")[0] + newGradePortion);

            } else {
                courseString += "|grade:";
                courseString += "report1=" + ((gradeType.equals("report1")) ? grade : "null");
                courseString += ",report2=" + ((gradeType.equals("report2")) ? grade : "null");
                courseString += ",exam=" + ((gradeType.equals("exam")) ? grade : "null");
                courseString += ",final=" + ((gradeType.equals("final")) ? grade : "null");

                RPClassStringsAsMap.put(courseName, courseString);
            }
        }


        //parse credit summary courses

        //cs: 2021,08,DIGITAL MEDIA,S2,2,100,0.5,

        HashMap<String, String> CSCourseStringsAsMap = new HashMap<>();

        for (String creditSummaryString : CSClassStrings) {
            String semester = switch (creditSummaryString.split(",")[3]) {
                case "S1" -> "fall";
                case "S2" -> "spring";
                default -> "null";
            };

            String courseName = creditSummaryString.split(",")[2] + "&semester=" + semester;
            String grade = "|grade:" + creditSummaryString.split(",")[5];

            String csString = courseName + grade;
            if (!RPClassStringsAsMap.containsKey(courseName)) {
                CSCourseStringsAsMap.put(courseName, csString);
            }
        }


        for (Map.Entry<String, String> reportCardCourseString : RPClassStringsAsMap.entrySet()) {
            String trueCourseName = reportCardCourseString.getKey().split("&")[0];
            String teacher = reportCardCourseString.getValue().split("teacher=")[1].split("\\|")[0];
            int semester = switch (reportCardCourseString.getKey().split("&semester=")[1]) {
                case "fall" -> 1;
                case "spring" -> 2;
                default -> -1;
            };

            int grade = calculateGradeRC(reportCardCourseString.getValue().split("\\|grade:")[1]);

            if (grade == -1) {
                continue;
            }

            double gpa = guessGpa(trueCourseName,
                    reportCardCourseString.getValue().split("\\|sectionIndex=")[1].split("\\|")[0]);

            Course course = new Course(trueCourseName, teacher, semester, gpa, grade);
            output.add(course);
        }


        for (Map.Entry<String, String> creditSummaryCourseString : CSCourseStringsAsMap.entrySet()) {
            int grade = Integer.parseInt(creditSummaryCourseString.getValue().split("\\|grade:")[1]);
            String teacher = "null";
            int semester = switch (creditSummaryCourseString.getKey().split("&semester=")[1]) {
                case "fall" -> 1;
                case "spring" -> 2;
                default -> -1;
            };
            String courseName = creditSummaryCourseString.getKey().split("&semester=")[0];
            double gpa = guessGpa(courseName, "null");

            Course course = new Course(courseName, teacher, semester, gpa, grade);
            output.add(course);
        }

        return output;
    }



    private static int calculateGradeRC(String gradeString) {
        double grade = -1;

        String rp1 = gradeString.split("report1=")[1].split(",")[0];
        String rp2 = gradeString.split("report2=")[1].split(",")[0];
        String exam = gradeString.split("exam=")[1].split(",")[0];
        String finalGrade = gradeString.split("final=")[1];

        if (!finalGrade.equals("null")) {
            grade = Double.parseDouble(finalGrade);
        } else {
            if (!rp1.equals("null")) {
                if (!rp2.equals("null")) {
                    if (!exam.equals("null") && !exam.equals("EX")) { //student took and received a grade for an exam
                        //ROUND((3/7)*(B24+C24)+(1/7)*D24)
                        grade = (3.0/7.0) * (Double.parseDouble(rp1) + Double.parseDouble(rp2)) + (1.0/7.0) * Double.parseDouble(exam);
                    } else {
                        grade = (Double.parseDouble(rp1) + Double.parseDouble(rp2)) / 2.0;
                    }
                } else {
                    grade = Double.parseDouble(rp1);
                }
            }
        }

        grade = Math.round(grade);

        return (int) grade;
    }


    public static HashMap<String, String[]> courseDataReference;

    //load class data into map.
    static {
        courseDataReference = new HashMap<String, String[]>();
        //classDataReference.put("", new String[]{"", ""});

        //Onramps courses.
        courseDataReference.put("D19625", new String[]{"OR Chem", "5.5"});
        courseDataReference.put("CHEM 1401", new String[]{"OR Chem", "6.0"});
        courseDataReference.put("D19759", new String[]{"OR Comp Sci", "5.5"});
        courseDataReference.put("COMP 1302", new String[]{"OR Comp Sci", "6.0"});
        courseDataReference.put("D19725", new String[]{"OR Physics", "5.5"});
        courseDataReference.put("PHYS 1301", new String[]{"OR Physics", "6.0"});
        courseDataReference.put("D15202", new String[]{"OR College Algebra", "5.5"});
        courseDataReference.put("MTH 1314", new String[]{"OR College Algebra", "6.0"});
        courseDataReference.put("D05764", new String[]{"OR Pre-Cal", "5.5"});
        courseDataReference.put("MATH 2312", new String[]{"OR Pre-Cal", "6.0"});
        courseDataReference.put("D15762", new String[]{"OR Statistics", "5.5"});
        courseDataReference.put("MATH 1342", new String[]{"OR Statistics", "6.0"});

        //AP courses
        courseDataReference.put("04443", new String[]{"AP Lit & Comp", "6.0"});
        courseDataReference.put("04343", new String[]{"AP Lang & Comp", "6.0"});
        courseDataReference.put("05643", new String[]{"AP Calc AB", "6.0"});
        courseDataReference.put("05653", new String[]{"AP Calc BC", "6.0"});
        courseDataReference.put("05783", new String[]{"AP Statistics", "6.0"});
        courseDataReference.put("01774", new String[]{"AP Comp Sci Prin", "6.0"});
        courseDataReference.put("08763", new String[]{"AP Biology", "6.0"});
        courseDataReference.put("08813", new String[]{"AP Environ Sci", "6.0"});
        courseDataReference.put("08743", new String[]{"AP Chemistry", "6.0"});
        courseDataReference.put("08753", new String[]{"AP Physics 1", "6.0"});
        courseDataReference.put("08754", new String[]{"AP Physics 2", "6.0"});
        courseDataReference.put("08773", new String[]{"AP Physics C", "6.0"});
        courseDataReference.put("03743", new String[]{"AP Human Geo", "6.0"});
        courseDataReference.put("03333", new String[]{"AP World History", "6.0"});
        courseDataReference.put("03103", new String[]{"AP Euro History", "6.0"});
        courseDataReference.put("03113", new String[]{"AP US History", "6.0"});
        courseDataReference.put("03121", new String[]{"AP Government", "6.0"});
        courseDataReference.put("03131", new String[]{"AP MacroEconomics", "6.0"});
        courseDataReference.put("01063", new String[]{"AP Music Theory", "6.0"});
        courseDataReference.put("00703", new String[]{"AP Art History", "6.0"});
        courseDataReference.put("00736", new String[]{"AP Art Studio 4", "6.0"});
        courseDataReference.put("00716", new String[]{"AP Studio Art 2d", "6.0"});
        courseDataReference.put("00726", new String[]{"AP Studio Art 3d", "6.0"});
        courseDataReference.put("00723", new String[]{"AP Studio Art Drawing", "6.0"});
        courseDataReference.put("02703", new String[]{"AP Span Lang", "6.0"});
        courseDataReference.put("02693", new String[]{"AP Span Lit", "6.0"});
        courseDataReference.put("02553", new String[]{"AP French", "6.0"});

        //English classes
        courseDataReference.put("04123", new String[]{"English 1", "5.0"});
        courseDataReference.put("04113", new String[]{"English 1 HON", "5.5"});
        courseDataReference.put("04223", new String[]{"English 2", "5.0"});
        courseDataReference.put("04213", new String[]{"English 2 HON", "5.5"});
        courseDataReference.put("04323", new String[]{"English 3", "5.0"});
        courseDataReference.put("04423", new String[]{"English 4", "5.0"});


        //Math classes
        courseDataReference.put("05103", new String[]{"Algebra 1", "5.0"});
        courseDataReference.put("05203", new String[]{"Algebra 1 HON", "5.5"});
        courseDataReference.put("05603", new String[]{"Geometry", "5.0"});
        courseDataReference.put("05353", new String[]{"Geometry HON", "5.5"});
        courseDataReference.put("05363", new String[]{"Algebra 2", "5.0"});
        courseDataReference.put("05383", new String[]{"Algebra 2 HON", "5.5"});
        courseDataReference.put("05753", new String[]{"Pre-Calculus", "5.0"});
        courseDataReference.put("05763", new String[]{"Pre-Calculus HON", "5.5"});

        //Science classes
        courseDataReference.put("08523", new String[]{"Biology", "5.0"});
        courseDataReference.put("08513", new String[]{"Biology HON", "5.5"});
        courseDataReference.put("08613", new String[]{"Chemistry", "5.0"});
        courseDataReference.put("08623", new String[]{"Chemistry HON", "5.5"});
        courseDataReference.put("08723", new String[]{"Physics", "5.0"});
        courseDataReference.put("08713", new String[]{"Physics HON", "5.5"});

        //History classes
        courseDataReference.put("03703", new String[]{"World Geo", "5.0"});
        courseDataReference.put("03713", new String[]{"World Geo HON", "5.5"});
        courseDataReference.put("03303", new String[]{"World Hist", "5.0"});
        courseDataReference.put("03313", new String[]{"World Hist HON", "5.5"});
        courseDataReference.put("03203", new String[]{"US History", "5.0"});
        courseDataReference.put("03401", new String[]{"Government", "5.0"});
        courseDataReference.put("03802", new String[]{"Economics", "5.0"});
        courseDataReference.put("03561", new String[]{"Intro Psych", "5.0"});
        courseDataReference.put("03581", new String[]{"Sociology", "5.0"});
        courseDataReference.put("02033", new String[]{"ASL 1", "5.0"});
        courseDataReference.put("02043", new String[]{"ASL 2", "5.0"});
        courseDataReference.put("02053", new String[]{"ASL 3", "5.0"});
        courseDataReference.put("02513", new String[]{"French 1", "5.0"});
        courseDataReference.put("02523", new String[]{"French 2", "5.0"});
        courseDataReference.put("02533", new String[]{"French 3", "5.5"});
        courseDataReference.put("02713", new String[]{"Spanish 1", "5.0"});
        courseDataReference.put("02723", new String[]{"Spanish 2", "5.0"});
        courseDataReference.put("02773", new String[]{"Spanish 1 NS", "5.0"});
        courseDataReference.put("02753", new String[]{"Spanish 2 NS", "5.0"});
        courseDataReference.put("02733", new String[]{"Spanish 3", "5.5"});
        courseDataReference.put("00613", new String[]{"Art 1", "5.0"});

        //other classes
        courseDataReference.put("29758", new String[]{"Computer Science 2", "5.0"});
    }

    private static double guessGpa(String courseName, String sectionIndex) {
        double gpaPrediction = 5.0; //default gpa

        if (sectionIndex.contains("AP") || sectionIndex.contains("DC") || sectionIndex.contains("OR")) {
            gpaPrediction = 6.0;
        }

        if (("" + sectionIndex.charAt(0)).equals("h")) {
            gpaPrediction = 5.5;
        }

        if (courseName.contains("HON") || courseName.contains("PAP")) {
            gpaPrediction = 5.5;
        }

        if (courseName.contains("OR") || courseName.contains("AP")) {
            gpaPrediction = 6.0;
        }

        return gpaPrediction;
    }



}
