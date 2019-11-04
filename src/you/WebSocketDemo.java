package you;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.RemoteEndpoint.Basic;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/demoo")
public class WebSocketDemo {
	
		private String appId;
		private Basic  basic;
		private String ssnId;

	    //连接时执行
	    @OnOpen
	    public void onOpen(@PathParam("appId") String appId, Session session) {
	    	System.out.println("connect...................");
	    	this.appId = appId;
	    	this.basic = session.getBasicRemote();
	    	
	    	ssnId = (String) session.getId();
	    	if(ssnId == null) return;
	    	
	    	StorageDemo.map.put(ssnId, this);
	    }
	    
	    //关闭时执行
	    @OnClose
	    public void onClose(){
	    	System.out.println("close...................");
	    	doClean();
	    }
	    
	    //收到消息时执行
	    @OnMessage
	    public void onMessage(String message, Session session) throws Exception {
	    	System.out.println(message);
	    	Set<Map.Entry<String, WebSocketDemo>> enu = StorageDemo.map.entrySet();
	    	Iterator it = enu.iterator();
	    	while (it.hasNext()) {
	    		Entry<String, WebSocketDemo> w = (Entry<String, WebSocketDemo>) it.next();
				w.getValue().sendMsg(message);
			}
	    }
	    
	    //连接错误时执行
	    @OnError
	    public void onError(Session session, Throwable error){
	    	System.out.println("error................");
	    	doClean();
	    }
	    
	    private void doClean() {
	    	StorageDemo.map.remove(ssnId);
	    }
	    
	    /**
	     * 发送消息
	     * @param msg
	     */
	    public void sendMsg(String msg) {
	    	System.out.println(msg);

	    	try {
				basic.sendText(msg);
			} catch (Exception e) {
				System.out.println(e);
			}
	    }
	    
	    

	


}
