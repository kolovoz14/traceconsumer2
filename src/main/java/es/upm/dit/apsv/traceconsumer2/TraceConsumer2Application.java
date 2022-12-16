package es.upm.dit.apsv.traceconsumer2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import es.upm.dit.apsv.traceconsumer2.Repository.TraceRepository;
import es.upm.dit.apsv.traceconsumer2.model.Trace;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.function.Consumer;

import es.upm.dit.apsv.traceconsumer2.model.TransportationOrder;
import es.upm.dit.apsv.traceconsumer2.Repository.TransportationOrderRepository;


@SpringBootApplication
public class TraceConsumer2Application {

	public static final Logger log = LoggerFactory.getLogger(TraceConsumer2Application.class);

	public static void main(String[] args) {
		SpringApplication.run(TraceConsumer2Application.class, args);
	}

	@Component
	class DemoCommandLineRunner implements CommandLineRunner {

		@Override
		public void run(String... args) throws Exception {
		}

		@Autowired
		private TraceRepository traceRepository;

		@Autowired
		private TransportationOrderRepository torderRepository;

		// @Autowired
		// private Environment env;

		// traceconsumer1_1
		// @Bean("consumer")
		// public Consumer<Trace> checkTrace() {
		// 	return t -> {
		// 		t.setTraceId(t.getTruck() + t.getLastSeen());
		// 		traceRepository.save(t);
		// 	};
		// }

		// // traceconsumer1_2
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

		// traceconsumer2
		@Bean("consumer")
		public Consumer<Trace> checkTrace() {
			return t -> {
				t.setTraceId(t.getTruck() + t.getLastSeen());
				traceRepository.save(t);
				TransportationOrder result = null;
				try
				{
					result = torderRepository.findByTruckAndSt(t.getTruck(), 0);
				}

				catch(Exception e)	// if sotg goes wrong
				{
					result = null;
				}

				if (result != null && result.getSt() == 0) {
					result.setLastDate(t.getLastSeen());
					result.setLastLat(t.getLat());
					result.setLastLong(t.getLng());
					if (result.distanceToDestination() < 10)
						result.setSt(1);
					torderRepository.save(result);
					log.info("Order updated: " + result);
				}
			};

		}
















	}




























	// TransportationOrder
	// @Component
	// class DemoCommandLineRunner implements CommandLineRunner {

	// 	@Autowired
	// 	private TransportationOrderRepository torderRepository;

	// 	@Override
	// 	public void run(String... args) throws Exception {

	// 		TransportationOrder t = new TransportationOrder();
	// 		t.setToid("MATRICULA");
	// 		t.setTruck("MATRICULA" + System.currentTimeMillis());
	// 		t.setOriginDate(100000000);
	// 		t.setDstDate(t.getOriginDate() + (1000 * 60 * 12));
	// 		t.setOriginLat(0.0);
	// 		t.setOriginLong(0);
	// 		t.setDstLat(44);
	// 		t.setDstLong(88);
	// 		t.setSt(0);
	// 		torderRepository.save(t);

	// 	}
	// }


}
