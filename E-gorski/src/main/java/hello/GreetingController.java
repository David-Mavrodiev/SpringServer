package hello;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {
	
	final String template = "From %s " + System.lineSeparator() + " %s "+ System.lineSeparator() + "To %s";
    private final AtomicLong counter = new AtomicLong();
    
    @RequestMapping("/AddSignal")
    public void addSignal(@RequestParam(value="firstName", defaultValue="None") String firstName, @RequestParam(value="lastName", defaultValue="None") String lastName, @RequestParam(value="signalDescription", defaultValue="None") String signalDescription, @RequestParam(value="telephoneNumber", defaultValue="None") String telephoneNumber, @RequestParam(value="lat", defaultValue="None") String lat, @RequestParam(value="lng", defaultValue="None") String lng) throws UnsupportedEncodingException, ClassNotFoundException, SQLException {
    	//http://localhost:8080/AddSignal?firstName=Георги&lastName=Иванов&signalDescription=Пожар&telephoneNumber=08878760629
    	System.out.println(firstName + " " + lastName + " " + signalDescription + " " + telephoneNumber);
    	ConnectionToOracleDB.addSignal(firstName, lastName, signalDescription, telephoneNumber, lat, lng);
    	ConnectionToOracleDB.addSignalResponse(ConnectionToOracleDB.countOfNodes(), "Сигналът все още не е проверен");
    }
    
    @RequestMapping("/CheckSignal")
    public @ResponseBody String checkSignal(@RequestParam(value = "number", defaultValue="None") String number) throws UnsupportedEncodingException, ClassNotFoundException, SQLException {
    	//http://localhost:8080/CheckSignal?number=1
    	System.out.println(number);
    	String result = ConnectionToOracleDB.checkForSignalResponse(Integer.parseInt(number));
    	return "<span style=\"font-size: 1.5em;\">" + result +"</span>";
    }
    
    @RequestMapping("/GetNextNumber")
    public @ResponseBody String getCount() throws ClassNotFoundException, SQLException{
    	int count = ConnectionToOracleDB.countOfNodes();
    	return "<span style=\"font-size: 1.5em\">" + Integer.toString(count) + "</span>";
    }
}