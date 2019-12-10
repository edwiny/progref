package com.example.regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexChecker {

    public static void main(String[] args) {
        String regexStr = "atl-vertigo--shard-(jira|conf).*(jres|sc|ps1|syn|dw).*";

        String input = "atl-vertigo--shard-jira-prod-us-10--sc--ps1";


        Pattern pattern = Pattern.compile(regexStr);

        Matcher matcher = pattern.matcher(input);

        if(matcher.matches()) {
            System.out.println("matched!");
        }
        else {
            System.out.println("NOT matched");
        }



    }


}
