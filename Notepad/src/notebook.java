import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.*;
import java.awt.MenuShortcut; 
import java.awt.MenuItem;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.TextArea;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.io.*;

public class notebook {
	private JFrame jf;
	private JTextArea content;
	private MenuBar menu; 
	private File file;
	//���ù����࣬����������
	Toolkit tk = Toolkit.getDefaultToolkit();
	Clipboard clipboard = tk.getSystemClipboard();
	public notebook() {
		//���ÿ���
		jf = new JFrame("���±�");
		jf.setFont(new Font("����", Font.PLAIN, 12));
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setBounds(100, 100, 500, 500);
		jf.setResizable(true);//��С�ɸı�
		jf.setVisible(true);//����Ϊ�ɼ�
		
		//���ò˵���
		menu = new MenuBar();
		jf.setMenuBar(menu);
		
		//����������ı���
		content = new JTextArea(50, 50);
		content.setVisible(true);
		content.requestFocusInWindow();
		jf.add(content);
		
		//�˵����������
		Menu filemenu = new Menu("�ļ�");
		Menu editmenu = new Menu("�༭");
		Menu formatmenu = new Menu("��ʽ");
		Menu viewmenu = new Menu("�鿴");
		Menu helpmenu = new Menu("����");
		menu.add(filemenu);
		menu.add(editmenu);
		menu.add(formatmenu);
		menu.add(viewmenu);
		menu.add(helpmenu);
		
		//�ļ��˵������
		MenuItem newitem = new MenuItem("�½�");
		newitem.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));
		filemenu.add(newitem);
		MenuItem openitem = new MenuItem("��");
		openitem.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		filemenu.add(openitem);
		MenuItem saveitem = new MenuItem("����");
		saveitem.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		filemenu.add(saveitem);
		MenuItem saveasitem = new MenuItem("���Ϊ");
		saveasitem.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
		filemenu.add(saveasitem);
		MenuItem setitem = new MenuItem("����");
		setitem.setShortcut(new MenuShortcut(KeyEvent.VK_U, false));
		filemenu.add(setitem);
		MenuItem exititem = new MenuItem("�˳�");
		exititem.setShortcut(new MenuShortcut(KeyEvent.VK_E, false));
		filemenu.add(exititem);
		
		//�༭�˵������
		MenuItem backitem = new MenuItem("����");
		backitem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
		editmenu.add(backitem);
		MenuItem cutitem = new MenuItem("����");
		cutitem.setShortcut(new MenuShortcut(KeyEvent.VK_T, false));
		editmenu.add(cutitem);
		MenuItem copyitem = new MenuItem("����");
		copyitem.setShortcut(new MenuShortcut(KeyEvent.VK_C, false));
		editmenu.add(copyitem);
		MenuItem pasteitem = new MenuItem("ճ��");
		pasteitem.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
		editmenu.add(pasteitem);
		MenuItem deleteitem = new MenuItem("ɾ��");
		deleteitem.setShortcut(new MenuShortcut(KeyEvent.VK_DELETE, false));
		editmenu.add(deleteitem);
		
		//��ʽ�˵������
		MenuItem autoitem = new MenuItem("�Զ�����");
		autoitem.setShortcut(new MenuShortcut(KeyEvent.VK_Z, false));
		formatmenu.add(autoitem);
		MenuItem worditem = new MenuItem("�����С");
		worditem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
		formatmenu.add(worditem);
		MenuItem coloritem = new MenuItem("������ɫ");
		coloritem.setShortcut(new MenuShortcut(KeyEvent.VK_W, false));
		formatmenu.add(coloritem);
		
		//�鿴�˵������
		MenuItem finditem = new MenuItem("����");
		finditem.setShortcut(new MenuShortcut(KeyEvent.VK_F, false));
		viewmenu.add(finditem);
		MenuItem findnextitem = new MenuItem("������һ��");
		findnextitem.setShortcut(new MenuShortcut(KeyEvent.VK_ENTER, false));
		viewmenu.add(findnextitem);
		
		//�����˵������
		MenuItem authoritem = new MenuItem("����������Ϣ");
		authoritem.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
		helpmenu.add(authoritem);
		
		//�ļ��˵����ü����¼�
		//�½�����
		newitem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				new notebook();
			}
		});
		//�ļ��򿪹���
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//���ļ��������
				FileDialog dialog = new FileDialog(new JFrame(), "��---", FileDialog.LOAD);
				dialog.setVisible(true);
				//��ȡ·�����ļ�
				String filepath = dialog.getDirectory() + dialog.getFile();
				file = new File(filepath);
				BufferedReader br = null;
				StringBuilder sb = new StringBuilder();
				try {
					br = new BufferedReader(new FileReader(file));
					String str = null;
					while((str = br.readLine()) != null)
					{
						sb.append(str).append("\n");
					}
					content.setText(sb.toString());
				}
				catch(FileNotFoundException e1){
					e1.printStackTrace();
				}
				catch(IOException e1) {
					e1.printStackTrace();
				}
				finally {
					if(br != null)
						try {
							br.close();
						}
						catch(IOException e1) {
							e1.printStackTrace();
						}
				}
			}
		});
		//���湦��
		saveitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(file != null){
					try {
						String filepath = file.getAbsolutePath();
						BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)));
						bw.write(content.getText().replaceAll("\r|\r\n|\n", "\r\n"));
						bw.flush();
						bw.close();
					}
					catch(IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					try{
						FileDialog dialog = new FileDialog(new JFrame(), "����---", FileDialog.SAVE);
						dialog.setVisible(true);
						String filepath = dialog.getDirectory() + dialog.getFile() + ".txt";
						file = new File(filepath);
						file.createNewFile();
						BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)));
						bw.write(content.getText().replaceAll("\r|\r\n|\n", "\r\n"));
						bw.flush();
						bw.close();
					}
					catch(IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		//���Ϊ����
		saveasitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					FileDialog dialog = new FileDialog(new JFrame(), "����---", FileDialog.SAVE);
					dialog.setVisible(true);
					String filepath = dialog.getDirectory() + dialog.getFile() + ".txt";
					file = new File(filepath);
					file.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)));
					bw.write(content.getText().replaceAll("\r|\r\n|\n", "\r\n"));
					bw.flush();
					bw.close();
				}
				catch(IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		//���ù���
		setitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.setText("��û��ʵ��Ŷ");
			}
		});
		//�˳�����
		exititem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(content.getText().equals(""))
					System.exit(0);
				if(file != null){
					try {
						String filepath = file.getAbsolutePath();
						BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)));
						bw.write(content.getText().replaceAll("\r|\r\n|\n", "\r\n"));
						bw.flush();
						bw.close();
						System.exit(0);
					}
					catch(IOException e1) {
						e1.printStackTrace();
					}
				}
				else {
					try{
						int choice = JOptionPane.showConfirmDialog(jf, ("�Ƿ�Ҫ����?"), ("�����ļ�"), JOptionPane.YES_NO_CANCEL_OPTION);
						if(choice == JOptionPane.NO_OPTION)
							System.exit(0);
						else if(choice == JOptionPane.CANCEL_OPTION) {
							
						}
						else if(choice == JOptionPane.YES_OPTION) {
							FileDialog dialog = new FileDialog(new JFrame(), "����---", FileDialog.SAVE);
							dialog.setVisible(true);
							String filepath = dialog.getDirectory() + dialog.getFile() + ".txt";
							file = new File(filepath);
							file.createNewFile();
							BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filepath)));
							bw.write(content.getText().replaceAll("\r|\r\n|\n", "\r\n"));
							bw.flush();
							bw.close();
							System.exit(0);
						}
					}
					catch(IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		//�༭�˵����ü����¼�
		//���ع���
		backitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.setText("��û��ʵ��Ŷ");
			}
		});
		
		//��������
		cutitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = content.getSelectedText();
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, null);
				if(text.length() == 0){
					return;
				}
				else {
					content.replaceRange("", content.getSelectionStart(), content.getSelectionEnd());
				}
			}
		});
		
		//���ƹ���
		copyitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = content.getSelectedText();
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, null);
			}
		});
		
		//ճ������
		pasteitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Transferable contents = clipboard.getContents(this);
				String str = null;
				try {
					str = contents.getTransferData(DataFlavor.stringFlavor).toString();
				}catch(UnsupportedFlavorException e1) {
					e1.printStackTrace();
				}catch(IOException e1) {
					e1.printStackTrace();
				}
				if(str == null) {
					return;
				}
				try {
					content.replaceRange(str, content.getSelectionStart(), content.getSelectionEnd());
				}catch(Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		//ɾ������
		deleteitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.replaceRange("", content.getSelectionStart(), content.getSelectionEnd());
			}
		});
		
		//��ʽ�˵�������
		//�Զ����и�ʽ
		autoitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(content.getLineWrap()) {
					JOptionPane.showMessageDialog(jf, "�Զ����й����ѹر�", "ע��", JOptionPane.INFORMATION_MESSAGE);
					content.setLineWrap(false);
				}	
				else{
					JOptionPane.showMessageDialog(jf, "�Զ����й����ѿ���", "ע��", JOptionPane.INFORMATION_MESSAGE);
					content.setLineWrap(true);
				}
			}
		});
		
		//���ڲ˵�����
		//������Ϣ
		authoritem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jf, "��ïʢ��˧�磬��ͬ����", "�����һ����ʵ", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		//�رճ����¼�
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(jf, "���ߣ���ïʢ\nϱ������������Ư������������Ů", "��������...", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new notebook();
	}
}
