package com.example.regex;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class RegexChecker {

    public static void main(String[] args) {
        String regexPattern = "atl-vertigo--shard-(jira|conf).*(jres|sc|ps1|syn|dw).*";
        String simplePattern = "atl-vertigo--shard";


        String input = "atl-vertigo--shard-jira-prod-us-10--sc--ps1";


        Pattern pattern = Pattern.compile(regexPattern);

        Matcher matcher = pattern.matcher(input);

        if(matcher.matches()) {
            System.out.println("Regex matched!");
        }
        else {
            System.out.println("Regex NOT matched");
        }


        if(input.startsWith(simplePattern)) {
            System.out.println("Simple pattern matched");
        }
        else {
            System.out.println("Simple pattern NOT matched");
        }




    }


}
