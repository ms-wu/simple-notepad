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
	//设置工具类，包含剪贴板
	Toolkit tk = Toolkit.getDefaultToolkit();
	Clipboard clipboard = tk.getSystemClipboard();
	public notebook() {
		//设置框体
		jf = new JFrame("记事本");
		jf.setFont(new Font("宋体", Font.PLAIN, 12));
		jf.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jf.setBounds(100, 100, 500, 500);
		jf.setResizable(true);//大小可改变
		jf.setVisible(true);//设置为可见
		
		//设置菜单栏
		menu = new MenuBar();
		jf.setMenuBar(menu);
		
		//创建并添加文本框
		content = new JTextArea(50, 50);
		content.setVisible(true);
		content.requestFocusInWindow();
		jf.add(content);
		
		//菜单栏添加内容
		Menu filemenu = new Menu("文件");
		Menu editmenu = new Menu("编辑");
		Menu formatmenu = new Menu("格式");
		Menu viewmenu = new Menu("查看");
		Menu helpmenu = new Menu("帮助");
		menu.add(filemenu);
		menu.add(editmenu);
		menu.add(formatmenu);
		menu.add(viewmenu);
		menu.add(helpmenu);
		
		//文件菜单栏设计
		MenuItem newitem = new MenuItem("新建");
		newitem.setShortcut(new MenuShortcut(KeyEvent.VK_N, false));
		filemenu.add(newitem);
		MenuItem openitem = new MenuItem("打开");
		openitem.setShortcut(new MenuShortcut(KeyEvent.VK_O, false));
		filemenu.add(openitem);
		MenuItem saveitem = new MenuItem("保存");
		saveitem.setShortcut(new MenuShortcut(KeyEvent.VK_S, false));
		filemenu.add(saveitem);
		MenuItem saveasitem = new MenuItem("另存为");
		saveasitem.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
		filemenu.add(saveasitem);
		MenuItem setitem = new MenuItem("设置");
		setitem.setShortcut(new MenuShortcut(KeyEvent.VK_U, false));
		filemenu.add(setitem);
		MenuItem exititem = new MenuItem("退出");
		exititem.setShortcut(new MenuShortcut(KeyEvent.VK_E, false));
		filemenu.add(exititem);
		
		//编辑菜单栏设计
		MenuItem backitem = new MenuItem("撤销");
		backitem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
		editmenu.add(backitem);
		MenuItem cutitem = new MenuItem("剪贴");
		cutitem.setShortcut(new MenuShortcut(KeyEvent.VK_T, false));
		editmenu.add(cutitem);
		MenuItem copyitem = new MenuItem("复制");
		copyitem.setShortcut(new MenuShortcut(KeyEvent.VK_C, false));
		editmenu.add(copyitem);
		MenuItem pasteitem = new MenuItem("粘贴");
		pasteitem.setShortcut(new MenuShortcut(KeyEvent.VK_P, false));
		editmenu.add(pasteitem);
		MenuItem deleteitem = new MenuItem("删除");
		deleteitem.setShortcut(new MenuShortcut(KeyEvent.VK_DELETE, false));
		editmenu.add(deleteitem);
		
		//格式菜单栏设计
		MenuItem autoitem = new MenuItem("自动换行");
		autoitem.setShortcut(new MenuShortcut(KeyEvent.VK_Z, false));
		formatmenu.add(autoitem);
		MenuItem worditem = new MenuItem("字体大小");
		worditem.setShortcut(new MenuShortcut(KeyEvent.VK_B, false));
		formatmenu.add(worditem);
		MenuItem coloritem = new MenuItem("字体颜色");
		coloritem.setShortcut(new MenuShortcut(KeyEvent.VK_W, false));
		formatmenu.add(coloritem);
		
		//查看菜单栏设计
		MenuItem finditem = new MenuItem("查找");
		finditem.setShortcut(new MenuShortcut(KeyEvent.VK_F, false));
		viewmenu.add(finditem);
		MenuItem findnextitem = new MenuItem("查找下一个");
		findnextitem.setShortcut(new MenuShortcut(KeyEvent.VK_ENTER, false));
		viewmenu.add(findnextitem);
		
		//帮助菜单栏设计
		MenuItem authoritem = new MenuItem("关于作者信息");
		authoritem.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
		helpmenu.add(authoritem);
		
		//文件菜单设置监听事件
		//新建功能
		newitem.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) {
				new notebook();
			}
		});
		//文件打开功能
		openitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//打开文件管理界面
				FileDialog dialog = new FileDialog(new JFrame(), "打开---", FileDialog.LOAD);
				dialog.setVisible(true);
				//获取路径加文件
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
		//保存功能
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
						FileDialog dialog = new FileDialog(new JFrame(), "保存---", FileDialog.SAVE);
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
		//另存为功能
		saveasitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					FileDialog dialog = new FileDialog(new JFrame(), "保存---", FileDialog.SAVE);
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
		//设置功能
		setitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.setText("还没有实现哦");
			}
		});
		//退出功能
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
						int choice = JOptionPane.showConfirmDialog(jf, ("是否要保存?"), ("保存文件"), JOptionPane.YES_NO_CANCEL_OPTION);
						if(choice == JOptionPane.NO_OPTION)
							System.exit(0);
						else if(choice == JOptionPane.CANCEL_OPTION) {
							
						}
						else if(choice == JOptionPane.YES_OPTION) {
							FileDialog dialog = new FileDialog(new JFrame(), "保存---", FileDialog.SAVE);
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
		//编辑菜单设置监听事件
		//撤回功能
		backitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.setText("还没有实现哦");
			}
		});
		
		//剪贴功能
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
		
		//复制功能
		copyitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String text = content.getSelectedText();
				StringSelection selection = new StringSelection(text);
				clipboard.setContents(selection, null);
			}
		});
		
		//粘贴功能
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
		
		//删除功能
		deleteitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				content.replaceRange("", content.getSelectionStart(), content.getSelectionEnd());
			}
		});
		
		//格式菜单栏设置
		//自动换行格式
		autoitem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(content.getLineWrap()) {
					JOptionPane.showMessageDialog(jf, "自动换行功能已关闭", "注意", JOptionPane.INFORMATION_MESSAGE);
					content.setLineWrap(false);
				}	
				else{
					JOptionPane.showMessageDialog(jf, "自动换行功能已开启", "注意", JOptionPane.INFORMATION_MESSAGE);
					content.setLineWrap(true);
				}
			}
		});
		
		//关于菜单设置
		//作者信息
		authoritem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(jf, "吴茂盛是帅哥，你同意吗？", "这个是一个事实", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		
		
		//关闭程序事件
		jf.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				JOptionPane.showMessageDialog(jf, "作者：吴茂盛\n媳妇儿：最最最漂亮的天仙美少女", "关于作者...", JOptionPane.INFORMATION_MESSAGE);
				System.exit(0);
			}
		});
	}
	
	public static void main(String[] args) {
		new notebook();
	}
}
