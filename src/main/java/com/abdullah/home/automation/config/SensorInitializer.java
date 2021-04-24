package com.abdullah.home.automation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.io.*;
import java.util.stream.Collectors;


/*
 * @created 24/04/2021 - 2:51 PM
 * @project HomeAutomation
 * @author C M Abdullah Khan
 * REF:
 */
@Configuration
public class SensorInitializer {

	private static final Logger log = LoggerFactory.getLogger(SensorInitializer.class);

	ProcessBuilder processBuilder = new ProcessBuilder();

	/** Initializes the weather sensor and connects to mosquitto broker
	 *
	 */

	@Value("${hardware.hardwareMode}")
	String devProfile;

	public Runnable sensorRunnable = new Runnable() {
		@Override
		public void run() {
			log.debug("Starting SensorInitializer...initialize called ::: ");

			String pwd = System.getProperty("user.dir");
			String fileName = devProfile.equals("with-hardware") ? "dht22Indoor.py" : "dht22IndoorMock.py";
			String filePath =  pwd+ "/"+fileName;
			String arg1 = "";
			String arg2 = "";
			String[] cmd = {"python3", filePath, arg1, arg2};

			try {
				log.info("Process Builder executing script ");
				processBuilder.command(cmd);
				log.info("processBuilder Starting");
				subProcessOutput(processBuilder.start());
				log.info("Subscription has been successfully done !!! ");
				//System.exit(0);
			} catch (IOException e) {
				log.error("Subscription failed !!! "+e.getLocalizedMessage());
				e.printStackTrace();
				//System.exit(-1);
			}catch (Exception e){
				log.error("Subscription failed !!! "+e.getLocalizedMessage());
				e.printStackTrace();
			}

		}
	};

	void subProcessOutput(Process p) {

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(p.getInputStream()));
		BufferedReader stdErr = new BufferedReader(new InputStreamReader(p.getErrorStream()));

		// read the output from the command
		String results = stdIn.lines().collect(Collectors.joining(System.lineSeparator()));
		// read any errors from the attempted command
		String errors = stdErr.lines().collect(Collectors.joining(System.lineSeparator()));

		if (results!=null){
			log.debug("Here is the standard output of the command -> ");
			log.debug(results);
		}

		if (!errors.trim().equals("")){
			log.error("Here is the standard error of the command (if any) -> ");
			log.error(errors);
		}
	}

}
