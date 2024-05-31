package dev.prognitio.cacao.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.jsoup.nodes.Document;
import org.jsoup.Jsoup;

import java.util.ArrayList;
import java.util.Arrays;

import dev.prognitio.cacao.Course;
import dev.prognitio.cacao.CourseImportUtil;
import dev.prognitio.cacao.R;
import dev.prognitio.cacao.log.LogType;
import dev.prognitio.cacao.log.Logger;

public class ImportCourseDataActivity extends AppCompatActivity {

    ImageButton returnButton;
    Button attemptAccessInfoButton;
    Button finishImportButton;

    EditText usernameField;
    EditText passwordField;

    ArrayList<Course> courses = new ArrayList<Course>();


    static String homeUrl = "https://teams.gccisd.net/selfserve/EntryPointHomeAction.do?parent=false";
    static String signInUrl = "https://teams.gccisd.net/selfserve/SignOnLoginAction.do?userLoginId=USERNAME&userPassword=PASSWORDNUMBER";
    static String creditsUrl = "https://teams.gccisd.net/selfserve/PSSViewStudentGradPlanCreditSummaryAction.do";
    static String reportCardUrl = "https://teams.gccisd.net/selfserve/PSSViewReportCardsAction.do";
    static String userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/109.0.0.0 Safari/537.36";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_import_course_data);
        Context context = getApplicationContext();

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        returnButton = findViewById(R.id.returntocourseactivity);

        returnButton.setOnClickListener(view -> {
            Intent switchActivityIntent = new Intent(context, CourseSetup.class);
            startActivity(switchActivityIntent);
        });

        attemptAccessInfoButton = findViewById(R.id.attemptAccessInfoButton);
        finishImportButton = findViewById(R.id.importSuccessButton);

        usernameField = findViewById(R.id.gccisdUsername);
        passwordField = findViewById(R.id.gccisdPassword);


        finishImportButton.setOnClickListener(view -> {
            //save courses data

            //Intent switchActivityIntent = new Intent(context, CourseSetup.class);
            //startActivity(switchActivityIntent);
        });


        attemptAccessInfoButton.setOnClickListener(view -> {

            String username = usernameField.getText().toString();
            String password = passwordField.getText().toString();

            boolean formatValid = true;

            try {
                int x = Integer.parseInt(username);
                int y = Integer.parseInt(password);
            } catch (Exception e) {
                formatValid = false;
            }

            if (username.length() != 7 || password.length() != 8) {
                formatValid = false;
            }

            if (formatValid) {
                try {
                    //attempt to access the grade portal and save info.

                    ArrayList<Document> pages = retrieveGradePages(username, password);

                    for (Document page : pages) {
                        System.out.println(page.toString());
                    }

                    ArrayList<Course> courses = CourseImportUtil.parseImportedDocuments(pages);

                    finishImportButton.setVisibility(View.VISIBLE);

                } catch (Exception e) {
                    //if something goes wrong
                }

            }

        });
    }

    private static ArrayList<Document> retrieveGradePages(String username, String password) {
        ArrayList<Document> pages = new ArrayList<>();

        String newSignInUrl = signInUrl.replace("USERNAME", username);
        newSignInUrl = newSignInUrl.replace("PASSWORDNUMBER", password);


        try {
            //setup webclient
            WebClient client = new WebClient();
            client.getOptions().setCssEnabled(false);
            client.getOptions().setJavaScriptEnabled(false);
            client.getOptions().setPrintContentOnFailingStatusCode(true);
            client.getCookieManager().setCookiesEnabled(true);
            client.addRequestHeader("user-agent", userAgent);
            client.getOptions().setRedirectEnabled(true);

            HtmlPage initialPage = client.getPage(homeUrl);
            HtmlPage signInPage = client.getPage(newSignInUrl);
            HtmlPage reportCardPage = client.getPage(reportCardUrl);
            HtmlPage creditSummaryPage = client.getPage(creditsUrl);
            Document reportCardDocument = Jsoup.parse(reportCardPage.asXml());
            Document creditSummaryDocument = Jsoup.parse(creditSummaryPage.asXml());
            pages.add(reportCardDocument);
            pages.add(creditSummaryDocument);

        } catch (Exception e) {
            Logger.log("Error encountered with loading and retrieving pages in signInAndRetrievePages();\n"
                    + e.getMessage() + "\n"
                    + Arrays.toString(e.getStackTrace()), LogType.ERROR, null);
        }

        return pages;
    }
}