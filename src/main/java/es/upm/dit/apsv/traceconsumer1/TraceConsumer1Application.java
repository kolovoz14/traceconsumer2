package es.upm.dit.apsv.traceconsumer1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import es.upm.dit.apsv.traceconsumer1.Repository.TraceRepository;
import es.upm.dit.apsv.traceconsumer1.model.Trace;

// import org.springframework.context.annotation.Bean;
// import java.util.function.Consumer;


import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import java.util.function.Consumer;
import es.upm.dit.apsv.traceconsumer1.model.TransportationOrder;


@SpringBootApplication
public class TraceConsumer1Application {

	public static final Logger log = LoggerFactory.getLogger(TraceConsumer1Application.class);

	//@Autowired
	//private TransportationOrderRepository torderRepository;

	public static void main(String[] args) {
		SpringApplication.run(TraceConsumer1Application.class, args);
	}

	@Component
	class DemoCommandLineRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
		}

		@Autowired
		private TraceRepository traceRepository;

		// @Autowired
		// private Environment env;

		// traceconsumer 0
		@Bean("consumer")
		public Consumer<Trace> checkTrace() {
			return t -> {
				t.setTraceId(t.getTruck() + t.getLastSeen());
				traceRepository.save(t);
			};
		}


		// traceconsumer 1
		// @Bean("consumer")
		// public Consumer<Trace> checkTrace() {
		// 	return t -> {
		// 		t.setTraceId(t.getTruck() + t.getLastSeen());
		// 		traceRepository.save(t);
		// 		String uri = env.getProperty("orders.server");
		// 		RestTemplate restTemplate = new RestTemplate();
		// 		TransportationOrder result = null;

		// 		try {
		// 			result = restTemplate.getForObject(uri
		// 					+ t.getTruck(), TransportationOrder.class);
		// 		} catch (HttpClientErrorException.NotFound ex) {
		// 			result = null;
		// 		}

		// 		if (result != null && result.getSt() == 0) {
		// 			result.setLastDate(t.getLastSeen());
		// 			result.setLastLat(t.getLat());
		// 			result.setLastLong(t.getLng());
		// 			if (result.distanceToDestination() < 10)
		// 				result.setSt(1);
		// 			restTemplate.put(uri, result, TransportationOrder.class);
		// 			log.info("Order updated: " + result);
		// 		}
		// 	};

		// }

	}
}
