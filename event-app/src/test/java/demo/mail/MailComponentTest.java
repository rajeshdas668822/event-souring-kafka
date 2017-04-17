package demo.mail;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;

import org.apache.camel.testng.CamelTestSupport;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;

/**
 * Created by rdas on 1/3/2017.
 */
public class MailComponentTest extends CamelTestSupport {

    @EndpointInject(uri = "mock:result")
    protected MockEndpoint resultEndpoint;

    @Produce(uri = "direct:start")
    protected ProducerTemplate template;


    @Test
    public void testSendNotMatchingMessage() throws Exception {
        resultEndpoint.expectedMessageCount(0);
        resultEndpoint.start();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        countDownLatch.await();

    }

    @Override
    protected RouteBuilder createRouteBuilder() {
        String mailId="rajeshranjan.d@gmail.com";
        String password="Rd668822";

        StringBuilder stringBuilder =
                new StringBuilder("imaps:")
                .append(mailId)
                .append("?password=")
                .append(password)
                .append("&delete=false")
                .append("&peek=false&unseen=true&consumer.delay=60000&closeFolder=false&disconnect=false");

        return new RouteBuilder() {
            public void configure() {
                from(stringBuilder.toString())
                        .log("${body}")
                       .to("mock:result");
            }
        };
    }

}
