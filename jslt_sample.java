import org.apache.camel.builder.RouteBuilder;

public class jslt_sample extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        from("timer:template?period=1000&repeatCount=1")
            .to("https://random-data-api.com/api/v2/users?size=1")
            .log("Before JSLT: ${body}")
            .to("jslt:file://mapping.json")
            .log("After JSLT: ${body}")
            ;

    }
}
