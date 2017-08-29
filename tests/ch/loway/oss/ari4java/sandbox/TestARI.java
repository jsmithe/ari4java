package ch.loway.oss.ari4java.sandbox;

import java.util.List;

import ch.loway.oss.ari4java.ARI;
import ch.loway.oss.ari4java.AriVersion;
import ch.loway.oss.ari4java.generated.*;
import ch.loway.oss.ari4java.tools.AriCallback;
import ch.loway.oss.ari4java.tools.RestException;
import ch.loway.oss.ari4java.tools.http.NettyHttpClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestARI {

	public static final String ASTERISK_ADDRESS = "http://172.20.33.228:8088/";
	public static final String ASTERISK_USER = "ari4java";
	public static final String ASTERISK_PASS = "1234";
	public static final String ASTERISK_APP = "hello,goodbye";

	public static void main(String[] args) {
		ARI ari = new ARI();
		NettyHttpClient hc = new NettyHttpClient();
		try {
			hc.initialize(ASTERISK_ADDRESS, ASTERISK_USER, ASTERISK_PASS);
			ari.setHttpClient(hc);
			ari.setWsClient(hc);
			ari.setVersion(AriVersion.ARI_2_0_0);
			ActionApplications ac = ari.getActionImpl(ActionApplications.class);
			List<? extends Application> alist = ac.list();
			for (Application app : alist) {
				System.out.println(app.getName());
			}
			ActionAsterisk aa = ari.getActionImpl(ActionAsterisk.class);
			AsteriskInfo ai = aa.getInfo("");
			System.out.println(ai.getSystem().getEntity_id());
			// Let's try async
			aa.getInfo("", new AriCallback<AsteriskInfo>() {
				@Override
				public void onSuccess(AsteriskInfo result) {
					System.out.println(result.getSystem().getEntity_id());
				}
				@Override
				public void onFailure(RestException e) {
					e.printStackTrace();
				}
			});
			aa.getGlobalVar("AMPMGRPASS", new AriCallback<Variable>() {
				@Override
				public void onSuccess(Variable result) {
					System.out.println(result.getValue());
				}
				@Override
				public void onFailure(RestException e) {
					e.printStackTrace();
				}
			});
			aa.setGlobalVar("WHATUP", "Hoo", new AriCallback<Void>() {
				@Override
				public void onSuccess(Void result) {
					System.out.println("Done");
				}
				@Override
				public void onFailure(RestException e) {
					e.printStackTrace();
				}
			});
			aa.getGlobalVar("WHATUP", new AriCallback<Variable>() {
				@Override
				public void onSuccess(Variable result) {
					System.out.println(result.getValue());
				}
				@Override
				public void onFailure(RestException e) {
					e.printStackTrace();
				}
			});
			System.out.println("Waiting for response...");
			ObjectMapper objectMapper = new ObjectMapper();
			ActionEvents ae = ari.getActionImpl(ActionEvents.class);
			ae.eventWebsocket(ASTERISK_APP, false, new AriCallback<Message>() {
				@Override
				public void onSuccess(Message result) {
					System.out.println("ws="+result);
					if(result instanceof StasisStart) {
						Channel channel = ((StasisStart)result).getChannel();
						System.out.printf("Channel %s has entered the application ", channel.getName());
						printObject(objectMapper, channel);

						try {
							ari.channels().hangup(channel.getId(), "");
						} catch (RestException e) {
							System.out.println(e);
						}
					} else if(result instanceof StasisEnd) {
						Channel channel = ((StasisEnd) result).getChannel();
						System.out.printf("Channel %s has left the application ", channel.getName());
						printObject(objectMapper, channel);
					}
				}
				
				@Override
				public void onFailure(RestException e) {
					e.printStackTrace();
				}
			});
			Thread.sleep(15000); // Allow wheels to turn before applying brakes
			ari.closeAction(ae);
			Thread.sleep(5000); 
			hc.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private static void printObject(ObjectMapper objectMapper, Object o) {
		try {
			System.out.println(objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(o));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
	}
}
