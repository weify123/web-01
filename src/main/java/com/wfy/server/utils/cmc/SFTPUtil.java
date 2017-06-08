package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerConfig;
import com.jcraft.jsch.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SFTPUtil {

	private static ChannelSftp connectWithDefaultConf() {
		return connect(BusinessServerConfig.getWangjinSftpHsot(), BusinessServerConfig.getWangjinSftpPort(), BusinessServerConfig.getWangjinSftpUsername(),
				BusinessServerConfig.getWangjinSftpPassword());
	}

	public static ChannelSftp connect(String host, int port, String username, String password) {
		ChannelSftp sftp = null;
		try {
			JSch jsch = new JSch();
			jsch.getSession(username, host, port);
			Session sshSession = jsch.getSession(username, host, port);
			sshSession.setPassword(password);
			Properties sshConfig = new Properties();
			sshConfig.put("StrictHostKeyChecking", "no");
			sshSession.setConfig(sshConfig);
			sshSession.connect();
			Channel channel = sshSession.openChannel("sftp");
			channel.connect();
			sftp = (ChannelSftp) channel;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sftp;
	}

	/**
	 * 上传文件
	 * 
	 * @param directory
	 *            上传的目录
	 * @param uploadFile
	 *            要上传的文件
	 * @param sftp
	 */
	public static void upload(String directory, String uploadFile) {
		try {
			ChannelSftp sftp = connectWithDefaultConf();
			createDir(directory, sftp);
			sftp.cd(directory);
			File file = new File(uploadFile);
			sftp.put(new FileInputStream(file), file.getName());
			sftp.disconnect();
			sftp.getSession().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 下载文件
	 * 
	 * @param directory
	 *            下载目录
	 * @param downloadFile
	 *            下载的文件
	 * @param saveFilePath
	 *            存在本地的路径
	 * @param sftp
	 */
	public static void download(String directory, String downloadFile, String saveFilePath) {
		try {
			ChannelSftp sftp = connectWithDefaultConf();
			sftp.cd(directory);
			File file = new File(saveFilePath);
			sftp.get(downloadFile, new FileOutputStream(file));
			sftp.disconnect();
			sftp.getSession().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除文件
	 * 
	 * @param directory
	 *            要删除文件所在目录
	 * @param deleteFile
	 *            要删除的文件
	 * @param sftp
	 */
	public static void delete(String directory, String deleteFile) {
		try {
			ChannelSftp sftp = connectWithDefaultConf();
			sftp.cd(directory);
			sftp.rm(deleteFile);
			sftp.disconnect();
			sftp.getSession().disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 列出目录下的文件
	 * 
	 * @param directory
	 *            要列出的目录
	 * @param sftp
	 * @return
	 * @throws SftpException
	 * @throws JSchException
	 */
	public static Vector<?> listFiles(String directory) throws SftpException, JSchException {
		ChannelSftp sftp = connectWithDefaultConf();
		Vector<?> v = sftp.ls(directory);
		sftp.disconnect();
		sftp.getSession().disconnect();
		return v;
	}

	/**
	 * 判断目录是否存在
	 */
	public static boolean isDirExist(String directory) {
		boolean isDirExistFlag = false;
		try {
			ChannelSftp sftp = connectWithDefaultConf();
			SftpATTRS sftpATTRS = sftp.lstat(directory);
			isDirExistFlag = sftpATTRS.isDir();

			sftp.disconnect();
			sftp.getSession().disconnect();
		} catch (SftpException sException) {
			if (ChannelSftp.SSH_FX_NO_SUCH_FILE == sException.id) {
				isDirExistFlag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return isDirExistFlag;
	}

	/**
	 * 验证路径是否存在，不存在则创建
	 * 
	 * @param directory
	 * @param sftp
	 * @return
	 */
	private static boolean createDir(String directory, ChannelSftp sftp) {
		try {
			String[] dirs = directory.split("\\/");
			StringBuffer path = new StringBuffer();
			for (int i = 1; i < dirs.length; i++) {
				path.append("/" + dirs[i]);
				if (!isDirExist(path.toString())) {
					sftp.mkdir(path.toString());
					sftp.cd(path.toString());
				} else {
					sftp.cd(path.toString());
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static void main(String[] args) {
		try {
			Pattern pattern = Pattern.compile(BusinessServerConfig.getWangjinInstitutionId() + "_refund_test_(\\d{8})_(\\d+)_(\\d+)\\.txt");
			String s = "123_refund_test_20160806_01_0031.txt";
			Matcher m = pattern.matcher(s);
			System.out.println(m.find());

			Vector<?> list = listFiles("/");
			for (Object o : list) {
				com.jcraft.jsch.ChannelSftp.LsEntry l = (com.jcraft.jsch.ChannelSftp.LsEntry) o;
				// System.out.println(l.getFilename());
			}

			String date = DateUtil.getCurrentDateStr();
			// 网金产品发布目录
			String filePath = "/um/" + date;
			System.out.println(SFTPUtil.isDirExist(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
