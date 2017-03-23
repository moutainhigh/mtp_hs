package com.core.util;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

public class FtpUtil {
	private static final Logger LOGGER=Logger.getLogger(FtpUtil.class);

	private static FTPClient ftpClient = new FTPClient();
    private static String encoding = System.getProperty("file.encoding");

    /**
     * Description: 向FTP服务器上传文件
     * @param url  FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param path FTP服务器保存目录,如果是根目录则为“/”
     * @param desFilename 上传到FTP服务器上的文件名
     * @param srcFileName 本地文件名
     * @return 成功返回true，否则返回false
     */
    public static boolean uploadFile(String url, int port, String username,
            String password, String path, String desFilename, String srcFileName) {
        boolean result = false;

        FileInputStream in = null;
        try {
            in = new FileInputStream(new File(srcFileName));
            //登陆服务器
            logServer(url,username,password,port);
            if (changeWorkingDirectory(path) ){
                result = ftpClient.storeFile(new String(desFilename.getBytes(encoding),"iso-8859-1"), in);
                if (result) {
                	LOGGER.info("上传成功!");
                }else{
                	LOGGER.info("上传失败!");
                }
            }
            in.close();
            //关闭连接
            closeConn(in,null);
        } catch (Exception e) {
        	in = null;
        	LOGGER.error("FtpUtil.uploadFile:"+e.getMessage(),e);
        } 

        return result;
    }

    /**
     * Description: 从FTP服务器下载文件
     * @param url FTP服务器hostname
     * @param port FTP服务器端口
     * @param username FTP登录账号
     * @param password FTP登录密码
     * @param remotePath FTP服务器上的相对路径
     * @param fileName 要下载的文件名
     * @param localPath 下载后保存到本地的路径
     * @return
     */
    public static boolean downFile(String url, int port, String username,
            String password, String remotePath, String fileName,
            String localPath) {
        boolean result = false;
        OutputStream os = null;
        try {
        	//登陆服务器
        	logServer(url,username,password,port);
            //改变目录
        	changeWorkingDirectory(remotePath);
        	//创建本地文件夹
         	createFileDir(remotePath);
            // 获取文件列表
            FTPFile[] fs = ftpClient.listFiles();
            for (FTPFile ff : fs) {
            	String fName = new String(ff.getName().getBytes("iso-8859-1"),"UTF-8");
                if (fName.equals(fileName)) {
                    //File localFile = new File(localPath + "/" + ff.getName());
                    File localFile = new File(localPath + File.separator + fileName);
                    os = new FileOutputStream(localFile);
                    result = ftpClient.retrieveFile(fileName, os);
                    os.close();
                }
            }
            
            //关闭连接
            try {
				closeConn(null,os);
			} catch (Exception e) {
			}
            result = true;
        } catch (Exception e) {
        	os = null;
        	LOGGER.error("FtpUtil.downFile:"+e.getMessage(),e);
        } 
        return result;
    }
    
    /**
     * 关闭连接
     * closeConn:(这里用一句话描述这个方法的作用). <br/>
     * @author yan.zhigang
     * @param in
     * @param os
     * @throws Exception
     */
    private static void closeConn(FileInputStream in,OutputStream os) throws Exception{
    	try {
			ftpClient.logout();
			if(null != in){
				in.close();
			}
			if(null != os){
				os.close();
			}
		} catch (Exception e) {
			LOGGER.error("FtpUtil.closeConn:"+e.getMessage(),e);
			throw e;
		}finally {
			if (ftpClient.isConnected()) {
                try {
                    ftpClient.disconnect();
                } catch (Exception ioe) {
                }
            }
			in = null;
			os = null;
		}
    }
    
    /**
     * 登陆服务器
     * logServer:(这里用一句话描述这个方法的作用). <br/>
     * @author yan.zhigang
     * @param url
     * @param username
     * @param password
     * @throws Exception
     */
    private static void logServer(String url,String username,String password,int port) throws Exception {
        try {
			ftpClient.connect(url,port);
	        ftpClient.login(username, password);
	        ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
	        ftpClient.setControlEncoding(encoding);
	        ftpClient.enterLocalPassiveMode();
	        ftpClient.setFileTransferMode(FTPClient.STREAM_TRANSFER_MODE);
	        // 检验是否连接成功
	        if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
            	throw new Exception();
            }
		} catch (Exception e) {
			LOGGER.error("FtpUtil.logServer:"+e.getMessage(),e);
			throw e;
		}
    }
    
    /**
     * 改变工作目录
     * changeWorkingDirectory:(这里用一句话描述这个方法的作用). <br/>
     * @author yan.zhigang
     * @param path
     * @return
     * @throws Exception
     */
    private static boolean changeWorkingDirectory(String path) throws Exception {
    	 boolean change = false;
    	 try {
    		//创建文件夹
    		 if(path.startsWith("/")){
    			 path = path.substring(1);
    		 }
         	//ftp切换到相应工作路径
			change = ftpClient.changeWorkingDirectory(path);
			if(!change){
				ftpClient.makeDirectory(path);
				change = ftpClient.changeWorkingDirectory(path);
			}
		} catch (Exception e) {
			LOGGER.error("FtpUtil.changeWorkingDirectory:"+e.getMessage(),e);
			throw e;
		}
    	 return change;
    }

    
    /**
     * 创建文件夹
     * createFileDir:(这里用一句话描述这个方法的作用). <br/>
     * @author yan.zhigang
     * @param path
     * @throws Exception
     */
    private static void createFileDir(String path) throws Exception{
    	try{
    		File file = new File(path);
        	if(!file.exists()){
        		if(file.mkdirs()){
        			LOGGER.info("创建文件夹成功："+path);
        		}else{
        			LOGGER.info("创建文件夹失败："+path);
        		}
        	}
    	}catch(Exception e){
    		LOGGER.error("FtpUtil.createFileDir:"+e.getMessage(),e);
			throw e;
    	}
    }
    
    public boolean deleteFile(String pathName){
    	boolean result = false;
    	
    	try {
			result = ftpClient.deleteFile(pathName);
		} catch (IOException e) {
		}

    	return result;
    }
    
    public static void main(String[] args) {
        //uploadFile("192.168.31.54", 21, "exch","123456", "send/exch/exch1", "SE_20160624_exch12345678_EF01.txt", "D:\\ftp\\send/exch\\SE_20160624_exch12345678_EF01.txt");
//        downFile("192.168.31.33", 22, "ftp",
//                "ftp", "/yzg", "qingsuanwenjian.txt", "D:/ftp/");
//        String path = "/a/b/c";
//        System.out.println(path.substring(0,1));
//        if("/".equals(path.substring(0,1))){
//        	path = path.substring(1);
//        	System.out.println(path);
//        }

//      downFile("192.168.30.126", 21, "tbank", "tbank123", "/zw", "1.txt", "D:/temp/");
    	//boolean b = uploadFile("192.168.30.126", 21, "tbank", "tbank123", "/zw", "test.txt", "d:\\temp\\test.txt");
    	
    	//System.out.println(b);
    	try {
			logServer("10.200.200.104", "4110014", "4110014", 21);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
