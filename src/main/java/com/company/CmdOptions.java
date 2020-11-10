package com.company;

import com.google.devtools.common.options.Option;
import com.google.devtools.common.options.OptionsBase;

public class CmdOptions extends OptionsBase {

    @Option(
            name = "help",
            abbrev = 'h',
            help = "Prints usage info.",
            defaultValue = "true"
    )
    public boolean help;

    @Option(
            name = "org",
            abbrev = 'o',
            help = "Org name",
            defaultValue = ""
    )
    public String orgName;

    @Option(
            name = "repo",
            abbrev = 'r',
            help = "Repository name",
            defaultValue = "engineering"
    )
    public String repoName;

    @Option(
            name = "team",
            abbrev = 't',
            help = "Team name",
            defaultValue = ""
    )
    public String teamName;

    @Option(
            name = "after",
            abbrev = 'a',
            help = "Created after date in yyyy-mm-dd format, inclusive",
            defaultValue = "2020-07-29"
    )
    public String afterDate;

    @Option(
            name = "before",
            abbrev = 'b',
            help = "Created before date in yyyy-mm-dd format. Optional, defaults to today",
            defaultValue = "2020-11-04"
    )
    public String beforeDate;
}