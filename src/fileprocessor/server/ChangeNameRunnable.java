package fileprocessor.server;

import java.io.File;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;

import db.domain.DirFile;
import db.domain.FileInfo;
import db.domain.HandleInfo;
import db.domain.ListInfor;
import network.server.QueueManager;

public class ChangeNameRunnable implements Runnable{
	//
	private Socket sock = null;
	private FileInfo fileInfo = null;
	private ObjectOutputStream out = null;
	private HandleInfo handleinfo;
	private QueueManager queuemanager;
	
	public ChangeNameRunnable(){
		//Constructor
		queuemanager = QueueManager.getInstance();
		this.handleinfo = queuemanager.getCngDirNameQueue().take();
		this.sock = this.handleinfo.getSock();
		this.fileInfo = this.handleinfo.getFileInfo();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			Thread.sleep(10);
			this.ChangeName(fileInfo.getUserId(), this.fileInfo.getCurrentPath(), this.fileInfo.getNewPath());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public List<DirFile> ChangeName(String userId, String currentPath, String newPath) throws IOException {
		// TODO client requesting currentPath => to DB
		String serverCurrentPath = null; // from DB
		// TODO client requesting newPath => to DB
		String serverNewPath = null; // from DB
		File file1 = new File(serverCurrentPath);
	    File file2 = new File(serverNewPath);
	    if (!file1.renameTo(file2)) {
	      System.out.println("Error, ChangeName Method is failed " + file1);
	    }
		out = new ObjectOutputStream(sock.getOutputStream());
		// Serializable
		ListInfor retList = new ListInfor();
		// TODO retList.setListInfor(list);
		out.writeObject(retList);
		return null;
	}
}
