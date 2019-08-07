package mikolo.springWebApp.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import lombok.AllArgsConstructor;
import mikolo.springWebApp.user.User;
import mikolo.springWebApp.user.UserService;

@Component
@AllArgsConstructor
public class UserUtilities {

	private UserService userService;

	public static String loggedUser() {
		String loggedUser = "";
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (!(auth instanceof AnonymousAuthenticationToken)) {
			loggedUser = auth.getName();
		}
		return loggedUser;
	}

	public static int getNrRoli(User user) {
		return user.getRoles().iterator().next().getId();
		// return user.getRoles().stream().mapToInt(Role::getId).findFirst().getAsInt();
	}

	public User getUserByUserName() {
		String userName = loggedUser();
		User user = userService.findByEmail(userName);
		return user;
	}

	public static List<User> usersDataLoader(File file) {
		List<User> userList = new ArrayList<User>();

		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("user");
			for (int i = 0; i < nList.getLength(); i++) {
				Node n = nList.item(i);
				if (n.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) n;
					User u = new User();
					u.setEmail(e.getElementsByTagName("email").item(0).getTextContent());
					u.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
					u.setName(e.getElementsByTagName("name").item(0).getTextContent());
					u.setLastName(e.getElementsByTagName("lastname").item(0).getTextContent());
					u.setActive(Integer.parseInt(e.getElementsByTagName("active").item(0).getTextContent()));
					u.setNrRoli(Integer.parseInt(e.getElementsByTagName("nrroli").item(0).getTextContent()));
					userList.add(u);
				}
			}

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return userList;
	}

}
