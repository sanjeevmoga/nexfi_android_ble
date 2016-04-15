package com.nexfi.yuanpeigen.nexfi_android_ble.model;

import android.util.Log;

import com.nexfi.yuanpeigen.nexfi_android_ble.activity.MainActivity;
import com.nexfi.yuanpeigen.nexfi_android_ble.application.BleApplication;
import com.nexfi.yuanpeigen.nexfi_android_ble.bean.UserMessage;
import com.nexfi.yuanpeigen.nexfi_android_ble.dao.BleDBDao;
import com.nexfi.yuanpeigen.nexfi_android_ble.util.ObjectBytesUtils;

import org.slf4j.impl.StaticLoggerBinder;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Random;
import java.util.UUID;

import io.underdark.Underdark;
import io.underdark.transport.Link;
import io.underdark.transport.Transport;
import io.underdark.transport.TransportKind;
import io.underdark.transport.TransportListener;
import io.underdark.util.nslogger.NSLogger;
import io.underdark.util.nslogger.NSLoggerAdapter;

public class Node implements TransportListener
{
	private boolean running;
	private MainActivity activity;
	private long nodeId;
	private Transport transport;

	private ArrayList<Link> links = new ArrayList<>();
	private int framesCount = 0;
	BleDBDao bleDBDao=new BleDBDao(BleApplication.getContext());//geng
	public Node(MainActivity activity)
	{
		this.activity = activity;

		do
		{
			nodeId = new Random().nextLong();
		} while (nodeId == 0);

		if(nodeId < 0)
			nodeId = -nodeId;

		configureLogging();

		EnumSet<TransportKind> kinds = EnumSet.of(TransportKind.BLUETOOTH, TransportKind.WIFI);
		//kinds = EnumSet.of(TransportKind.WIFI);
		//kinds = EnumSet.of(TransportKind.BLUETOOTH);

		this.transport = Underdark.configureTransport(
				234235,
				nodeId,
				this,
				null,
				activity.getApplicationContext(),
				kinds
		);
	}

	private void configureLogging()
	{
		NSLoggerAdapter adapter = (NSLoggerAdapter)
				StaticLoggerBinder.getSingleton().getLoggerFactory().getLogger(Node.class.getName());
		adapter.logger = new NSLogger(activity.getApplicationContext());
		adapter.logger.connect("192.168.5.203", 50000);

		Underdark.configureLogging(true);
	}

	public void start()
	{
		if(running)
			return;

		running = true;
		transport.start();
	}

	public void stop()
	{
		if(!running)
			return;

		running = false;
		transport.stop();
	}

	public ArrayList<Link> getLinks()
	{
		return links;
	}

	public int getFramesCount()
	{
		return framesCount;
	}
	//发送数据
	public void broadcastFrame(byte[] frameData)
	{
		if(links.size()==0){
			return;
		}


//		++framesCount;
////		activity.refreshFrames();
//
		for(Link link : links){
			Log.e("TAG", links.size() + "----------------for------links.size()------------------");
			link.sendFrame(frameData);
		}

	}

	//region TransportListener
	@Override
	public void transportNeedsActivity(Transport transport, ActivityCallback callback)
	{
		callback.accept(activity);
	}

	//连接
	@Override
	public void transportLinkConnected(Transport transport, Link link)
	{
		links.add(link);
		Log.e("TAG",links.size()+"------连接数");
		if(null!=links && links.size()>0){//搜索到设备后就发送请求，并把自己的信息携带过去
			UserMessage user=new UserMessage();
			user.userNick="大宝";
			user.userId= BleApplication.getUUID();
			user.messageType="REQUEST_USER_INFO";
			byte[] data= ObjectBytesUtils.ObjectToByte(user);
			broadcastFrame(data);
		}
//		activity.refreshPeers();
	}
	//断开连接
	@Override
	public void transportLinkDisconnected(Transport transport, Link link)
	{
		links.remove(link);
//		activity.refreshPeers();

		if(links.isEmpty())
		{
			framesCount = 0;
//			activity.refreshFrames();
		}
	}
	//接收数据，自动调用
	@Override
	public void transportLinkDidReceiveFrame(Transport transport, Link link, byte[] frameData)
	{
		Log.e("TAG",transport.toString()+"-------transportLinkDidReceiveFrame===="+link.toString()+"----data==="+new String(frameData));//2428422316790765964
		//接收到数据后将用户数据发送给对方
		UserMessage userMessage= (UserMessage) ObjectBytesUtils.ByteToObject(frameData);
		if("REQUEST_USER_INFO".equals(userMessage.messageType)){
			//收到请求之后，将自己的信息封装发给对方
			byte[] data="我收到了".getBytes();
			//把数据发送给对方
			UserMessage userM=new UserMessage();
			userM.userNick="longbaobao";
			userM.messageType="ROEPONSE_USER_INFO";
			userM.userId= UUID.randomUUID().toString();
			byte[] dataM= ObjectBytesUtils.ObjectToByte(userM);
			link.sendFrame(dataM);
			Log.e("TAG","send--------------------------------");
		}else if("ROEPONSE_USER_INFO".equals(userMessage.messageType)){
			//接收对方反馈的用户信息
			userMessage.nodeId=link.getNodeId();//这是很重要的一步，将所连接的link跟连接的用户绑定，这样通过nodeId就可以找到对应的link,这样就可以给指定的人发消息了
			//然后将接收到的用户信息保存到数据库
			if(!bleDBDao.findSameUserByUserId(userMessage.userId)){
				bleDBDao.add(userMessage);
			}
			activity.refreshFrames("USER".getBytes());
			Log.e("TAG","收到反馈-------------------------------------");
		}
	}
	//endregion
} // Node
