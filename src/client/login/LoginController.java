package client.login;

import client.base.*;
import client.facade.ClientFacade;
import client.misc.*;

import java.net.*;
import java.io.*;
import java.util.*;
import java.lang.reflect.*;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;


/**
 * Implementation for the login controller
 */
public class LoginController extends Controller implements ILoginController {

	private IMessageView messageView;
	private IAction loginAction;
	private ClientFacade clientFacade;
	
	/**
	 * LoginController constructor
	 * 
	 * @param view Login view
	 * @param messageView Message view (used to display error messages that occur during the login process)
	 */
	public LoginController(ILoginView view, IMessageView messageView) {

		super(view);
		
		this.messageView = messageView;
		clientFacade = ClientFacade.getSingleton();
		clientFacade.setLoginController(this);
	}
	
	public ILoginView getLoginView() {
		
		return (ILoginView)super.getView();
	}
	
	public IMessageView getMessageView() {
		
		return messageView;
	}
	
	/**
	 * Sets the action to be executed when the user logs in
	 * 
	 * @param value The action to be executed when the user logs in
	 */
	public void setLoginAction(IAction value) {
		
		loginAction = value;
	}
	
	/**
	 * Returns the action to be executed when the user logs in
	 * 
	 * @return The action to be executed when the user logs in
	 */
	public IAction getLoginAction() {
		
		return loginAction;
	}

	@Override
	public void start() {
		
		getLoginView().showModal();
	}

	@Override
	public void signIn() 
	{
		//log in user
		String username = getLoginView().getLoginUsername();
		String password = getLoginView().getLoginPassword();

		if(clientFacade.login(username, password)){
			System.out.println("Login Successful");
			getLoginView().closeModal();
			loginAction.execute();
		} 
		else
		{
			System.out.println("Login Failure");
			getMessageView().setMessage("Login Failed, player doesn't exist");
			getMessageView().setTitle("Login Failure");
			getMessageView().showModal();
		}
	}
	
	public void signIn(String username, String password) 
	{
		//log in user
		if(clientFacade.login(username, password)){
			System.out.println("Login Successful");
			getLoginView().closeModal();
			loginAction.execute();
		} 
		else
		{
			System.out.println("Login Failure");
			getMessageView().setMessage("Login Failed, player doesn't exist");
			getMessageView().setTitle("Login Failure");
			getMessageView().showModal();
		}
	}
	

	@Override
	public void register() 
	{
		//register new user (which, if successful, also logs them in)
		
		String username = getLoginView().getRegisterUsername();
		String password = getLoginView().getRegisterPassword();
		String passwordRepeat = getLoginView().getRegisterPasswordRepeat();
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(passwordRepeat);
		
		if(password.equals(passwordRepeat))
		{
			if(!username.isEmpty())
			{
				if(clientFacade.register(username, password))
				{
					System.out.println("Registration Successful");
					getLoginView().closeModal();
					loginAction.execute();
				}
				else
				{
					System.out.println("Registration Failure");
					getMessageView().setMessage("Registration Failed, this player already exist");
					getMessageView().setTitle("Bad Credentials");
					getMessageView().showModal();
				}
			}
			else
			{
				System.out.println("Registration Failure");
				getMessageView().setMessage("Registration Failed");
				getMessageView().setTitle("Have to have a username");
				getMessageView().showModal();
			}
		}
		else
		{
			//Error Message about passwords not matching
			System.out.println("Registration Failure");
			getMessageView().setMessage("Registration Failed");
			getMessageView().setTitle("Passwords don't match");
			getMessageView().showModal();
		}
		

		//register new user (which, if successful, also logs them in)
		// If register succeeded

	}

}

