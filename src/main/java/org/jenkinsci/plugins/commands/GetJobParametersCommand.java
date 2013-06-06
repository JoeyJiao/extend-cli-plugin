package org.jenkinsci.plugins.commands;

import hudson.Extension;
import hudson.cli.CLICommand;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.ParameterDefinition;
import hudson.model.ParametersDefinitionProperty;
import org.kohsuke.args4j.Argument;

@Extension
public class GetJobParametersCommand extends CLICommand{
	@Argument(metaVar = "JOB", usage = "Name of the Job", required = true)
	public AbstractProject<?,?> job;

	@Override
	public String getShortDescription() {
		return Messages.GetJobParameters_ShortDescription();
	}

	@Override
	protected int run() throws Exception {
		job.checkPermission(Item.EXTENDED_READ);
		try {
			ParametersDefinitionProperty property = job.getProperty(ParametersDefinitionProperty.class);
			if (property != null && property.getParameterDefinitions() !=null) {
				for (ParameterDefinition p : property.getParameterDefinitions()) {
					stdout.println(p.getDefaultParameterValue());
				}
			}
		} catch (NullPointerException e) {
			stderr.println(Messages.GetJobParametersCommand_NoParameters());
			return 1;
		}
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
