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
            defaultValue = "fivetran"
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
            defaultValue = "Liskov"
    )
    public String teamName;

    @Option(
            name = "after",
            abbrev = 'a',
            help = "Created after date in yyyy-mm-dd format, inclusive",
            defaultValue = "2021-07-28"
    )
    public String afterDate;

    @Option(
            name = "before",
            abbrev = 'b',
            help = "Created before date in yyyy-mm-dd format. Optional, defaults to today",
            defaultValue = "2022-01-27"
    )
    public String beforeDate;

    @Option(
            name = "name",
            abbrev = 'n',
            help = "Friendly name to the date range",
            defaultValue = "q34_fy2021"
    )
    public String friendlyName;
}