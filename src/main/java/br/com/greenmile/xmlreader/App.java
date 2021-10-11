package br.com.greenmile.xmlreader;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.greenmile.xmlreader.service.ProcessXmlService;
import br.com.greenmile.xmlreader.service.RoutePlanSenderService;
import lombok.extern.java.Log;

@Log
@SpringBootApplication
public class App implements CommandLineRunner {
	
	
	private final ProcessXmlService processXmlService;
	private final RoutePlanSenderService routePlanSenderService;
	
	public App(ProcessXmlService processXmlService, RoutePlanSenderService routePlanSenderService) {
		this.processXmlService = processXmlService;
		this.routePlanSenderService = routePlanSenderService;
	}

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		Thread.sleep(5000);
		
		new Thread() {
			@Override
			public void run() {
				log.info("Sending data from route plan A");
				routePlanSenderService.routePlanSender(processXmlService.processXml("rotas_shopping_1.xml"), 1l);
			};
		}.start();
		
		
		
		new Thread() {
			@Override
			public void run() {
				log.info("Sending data from route plan B");
				routePlanSenderService.routePlanSender(processXmlService.processXml("rotas_fast_food.xml"), 2l);
			};
		}.start();
		
		
	}

}
