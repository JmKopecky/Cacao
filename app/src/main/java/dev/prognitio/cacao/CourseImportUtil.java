package dev.prognitio.cacao;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;

import dev.prognitio.cacao.Course;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class CourseImportUtil {

    public static HashMap<String, String[]> classDataReference;

    //load class data into map.
    static {
        classDataReference = new HashMap<String, String[]>();
        //classDataReference.put("", new String[]{"", ""});

        //Onramps courses.
        classDataReference.put("D19625", new String[]{"OR Chem", "5.5"});
        classDataReference.put("CHEM 1401", new String[]{"OR Chem", "6.0"});
        classDataReference.put("D19759", new String[]{"OR Comp Sci", "5.5"});
        classDataReference.put("COMP 1302", new String[]{"OR Comp Sci", "6.0"});
        classDataReference.put("D19725", new String[]{"OR Physics", "5.5"});
        classDataReference.put("PHYS 1301", new String[]{"OR Physics", "6.0"});
        classDataReference.put("D15202", new String[]{"OR College Algebra", "5.5"});
        classDataReference.put("MTH 1314", new String[]{"OR College Algebra", "6.0"});
        classDataReference.put("D05764", new String[]{"OR Pre-Cal", "5.5"});
        classDataReference.put("MATH 2312", new String[]{"OR Pre-Cal", "6.0"});
        classDataReference.put("D15762", new String[]{"OR Statistics", "5.5"});
        classDataReference.put("MATH 1342", new String[]{"OR Statistics", "6.0"});

        //AP courses
        classDataReference.put("04443", new String[]{"AP Lit & Comp", "6.0"});
        classDataReference.put("04343", new String[]{"AP Lang & Comp", "6.0"});
        classDataReference.put("05643", new String[]{"AP Calc AB", "6.0"});
        classDataReference.put("05653", new String[]{"AP Calc BC", "6.0"});
        classDataReference.put("05783", new String[]{"AP Statistics", "6.0"});
        classDataReference.put("01774", new String[]{"AP Comp Sci Prin", "6.0"});
        classDataReference.put("08763", new String[]{"AP Biology", "6.0"});
        classDataReference.put("08813", new String[]{"AP Environ Sci", "6.0"});
        classDataReference.put("08743", new String[]{"AP Chemistry", "6.0"});
        classDataReference.put("08753", new String[]{"AP Physics 1", "6.0"});
        classDataReference.put("08754", new String[]{"AP Physics 2", "6.0"});
        classDataReference.put("08773", new String[]{"AP Physics C", "6.0"});
        classDataReference.put("03743", new String[]{"AP Human Geo", "6.0"});
        classDataReference.put("03333", new String[]{"AP World History", "6.0"});
        classDataReference.put("03103", new String[]{"AP Euro History", "6.0"});
        classDataReference.put("03113", new String[]{"AP US History", "6.0"});
        classDataReference.put("03121", new String[]{"AP Government", "6.0"});
        classDataReference.put("03131", new String[]{"AP MacroEconomics", "6.0"});
        classDataReference.put("01063", new String[]{"AP Music Theory", "6.0"});
        classDataReference.put("00703", new String[]{"AP Art History", "6.0"});
        classDataReference.put("00736", new String[]{"AP Art Studio 4", "6.0"});
        classDataReference.put("00716", new String[]{"AP Studio Art 2d", "6.0"});
        classDataReference.put("00726", new String[]{"AP Studio Art 3d", "6.0"});
        classDataReference.put("00723", new String[]{"AP Studio Art Drawing", "6.0"});
        classDataReference.put("02703", new String[]{"AP Span Lang", "6.0"});
        classDataReference.put("02693", new String[]{"AP Span Lit", "6.0"});
        classDataReference.put("02553", new String[]{"AP French", "6.0"});

        //English classes
        classDataReference.put("04123", new String[]{"English 1", "5.0"});
        classDataReference.put("04113", new String[]{"English 1 HON", "5.5"});
        classDataReference.put("04223", new String[]{"English 2", "5.0"});
        classDataReference.put("04213", new String[]{"English 2 HON", "5.5"});
        classDataReference.put("04323", new String[]{"English 3", "5.0"});
        classDataReference.put("04423", new String[]{"English 4", "5.0"});


        //Math classes
        classDataReference.put("05103", new String[]{"Algebra 1", "5.0"});
        classDataReference.put("05203", new String[]{"Algebra 1 HON", "5.5"});
        classDataReference.put("05603", new String[]{"Geometry", "5.0"});
        classDataReference.put("05353", new String[]{"Geometry HON", "5.5"});
        classDataReference.put("05363", new String[]{"Algebra 2", "5.0"});
        classDataReference.put("05383", new String[]{"Algebra 2 HON", "5.5"});
        classDataReference.put("05753", new String[]{"Pre-Calculus", "5.0"});
        classDataReference.put("05763", new String[]{"Pre-Calculus HON", "5.5"});

        //Science classes
        classDataReference.put("08523", new String[]{"Biology", "5.0"});
        classDataReference.put("08513", new String[]{"Biology HON", "5.5"});
        classDataReference.put("08613", new String[]{"Chemistry", "5.0"});
        classDataReference.put("08623", new String[]{"Chemistry HON", "5.5"});
        classDataReference.put("08723", new String[]{"Physics", "5.0"});
        classDataReference.put("08713", new String[]{"Physics HON", "5.5"});

        //History classes
        classDataReference.put("03703", new String[]{"World Geo", "5.0"});
        classDataReference.put("03713", new String[]{"World Geo HON", "5.5"});
        classDataReference.put("03303", new String[]{"World Hist", "5.0"});
        classDataReference.put("03313", new String[]{"World Hist HON", "5.5"});
        classDataReference.put("03203", new String[]{"US History", "5.0"});
        classDataReference.put("03401", new String[]{"Government", "5.0"});
        classDataReference.put("03802", new String[]{"Economics", "5.0"});
        classDataReference.put("03561", new String[]{"Intro Psych", "5.0"});
        classDataReference.put("03581", new String[]{"Sociology", "5.0"});
        classDataReference.put("02033", new String[]{"ASL 1", "5.0"});
        classDataReference.put("02043", new String[]{"ASL 2", "5.0"});
        classDataReference.put("02053", new String[]{"ASL 3", "5.0"});
        classDataReference.put("02513", new String[]{"French 1", "5.0"});
        classDataReference.put("02523", new String[]{"French 2", "5.0"});
        classDataReference.put("02533", new String[]{"French 3", "5.5"});
        classDataReference.put("02713", new String[]{"Spanish 1", "5.0"});
        classDataReference.put("02723", new String[]{"Spanish 2", "5.0"});
        classDataReference.put("02773", new String[]{"Spanish 1 NS", "5.0"});
        classDataReference.put("02753", new String[]{"Spanish 2 NS", "5.0"});
        classDataReference.put("02733", new String[]{"Spanish 3", "5.5"});
        classDataReference.put("00613", new String[]{"Art 1", "5.0"});
    }


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


        HashMap<String, String> RPClassStringsAsMap = new HashMap<>();

        for (String rpString : RPClassStrings) {
            String courseName = rpString.split("courseIndex=")[1].split(",")[0];
            String gradeType = rpString.split("gradeTypeIndex=")[1].split(",")[0];
            String teacher = rpString.split("teacherIndex=")[1].split(",")[0];
            String grade = (rpString.contains()) ?
        }

        return output;
    }



}
