package webadv.nchu17204223.p02;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.codec.digest.DigestUtils;

public class App {
	private static String fileName = "user.txt";
	private static List<String> ids = new ArrayList<String>();
	private static List<String> passwords = new ArrayList<String>();

	public static void main(String[] args) throws IOException {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
			addUser("Nchu17204223", "123456");
		}
		readFileToLists(file);
		String id = loginWithArgs(args);
//		String id = loginWithoutArgs();
		if (id != null) {
			System.out.println("账号" + id + ",登陆成功");
		} else {
			System.out.println("登录失败");
		}
	}

	private static String loginWithArgs(String[] args) {
		if (args.length != 2) {
			System.err.println("Please provide two inputs! The first is account number, the second is password!");
			System.exit(0);
		}
		for (int i = 0; i < ids.size(); i++) {
			if (args[0].equals(ids.get(i)) && sha256hex(args[1]).equals(passwords.get(i))) {
				return args[0];
			}
		}
		return null;
	}

	private static String loginWithoutArgs() {
		System.out.println("Please provide two inputs! The first is account number, the second is password!");
		Scanner input = new Scanner(System.in);
		String id = input.next();
		String password = sha256hex(input.next());
		for (int i = 0; i < ids.size(); i++) {
			if (id.equals(ids.get(i)) && password.equals(passwords.get(i))) {
				return id;
			}
		}
		return null;
	}

	private static void readFileToLists(File file) throws IOException {
		BufferedReader in = new BufferedReader(new FileReader(file));
		String str = "";
		boolean flag = true;
		while ((str = in.readLine()) != null) {
			if (flag) {
				ids.add(str);
				flag = false;
			} else {
				passwords.add(str);
				flag = true;
			}
		}
		in.close();
	}

	private static void addUser(String account, String password) throws IOException {
		BufferedWriter out = new BufferedWriter(new FileWriter(new File(fileName), true));
		out.write(account);
		out.newLine();
		out.write(sha256hex(password));
		out.newLine();
		out.close();
	}

	public static String sha256hex(String input) {
		return DigestUtils.sha256Hex(input);
	}

}
