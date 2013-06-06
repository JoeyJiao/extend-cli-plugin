package org.jenkinsci.plugins.commands;

import hudson.Extension;
import hudson.cli.CLICommand;
import hudson.model.AbstractProject;
import hudson.model.Item;
import hudson.model.ParametersDefinitionProperty;
import org.kohsuke.args4j.Argument;

@Extension
public class GetJobParameterCommand extends CLICommand{
	@Argument(metaVar = "JOB", usage = "Name of the Job", required = true)
	public AbstractProject<?,?> job;
	@Argument(metaVar = "PARAMETER", usage = "Name of the Parameter", index = 1, required = true)
	public String parameter;

	@Override
	public String getShortDescription() {
		return Messages.GetJobParameter_ShortDescription();
	}

	@Override
	protected int run() throws Exception {
		job.checkPermission(Item.EXTENDED_READ);
		try {
			ParametersDefinitionProperty property = job.getProperty(ParametersDefinitionProperty.class);
			stdout.println(property.getParameterDefinition(parameter).getDefaultParameterValue());
		} catch (NullPointerException e) {
			stderr.println(Messages.GetJobParameterCommand_NoSuchParameter());
			return 1;
		}
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
