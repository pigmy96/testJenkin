package com.pigmy.demospringboot.session;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pigmy.demospringboot.dao.AlbumDAO;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.model.CartInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class CartSession {
	@Autowired
	AlbumDAO albumDAO;
	@Autowired
	CartSession cartSession;

	   @SuppressWarnings("unchecked")
	public List<CartInfo> getListCartInSession(HttpServletRequest request) {
	 
		   List<CartInfo> cartInfo = (List<CartInfo>) request.getSession().getAttribute("cartInfo");
		   if (cartInfo == null) {
	           cartInfo = new ArrayList<CartInfo>();
	           request.getSession().setAttribute("cartInfo", cartInfo);
	       }
	 
	       return cartInfo;
	   }
	 
	   public void removeCartInSession(HttpServletRequest request, String id) {
		   List<CartInfo> cartInfo = getListCartInSession(request);
	       request.getSession().removeAttribute("cartInfo");
	       for (int i = 0; i < cartInfo.size(); i++){
	    	   if (cartInfo.get(i).getID().equals(id)){
	    		   System.out.println("cartID remove:" + id);
	    		   cartInfo.remove(i);
	    		   break;
	    	   }
	       }
	       for (int i = 0; i < cartInfo.size(); i++){
	    		   System.out.println("cartID remove - list:" + cartInfo.get(i).getID());
	    		   
	       }
	       request.getSession().setAttribute("cartInfo", cartInfo);
	   }
	   
	   public void removeListCartInSession(HttpServletRequest request) {
		   request.getSession().removeAttribute("cartInfo");
	   }
	   
	   public void saveCartInSession(HttpServletRequest request, CartInfo cart){
		   @SuppressWarnings("unchecked")
		   List<CartInfo> cartInfo = (List<CartInfo>) request.getSession().getAttribute("cartInfo");
	       request.getSession().removeAttribute("cartInfo");
	       cartInfo.add(cart);
	       request.getSession().setAttribute("cartInfo", cartInfo);
	   }
	   
	   public int isAdded (HttpServletRequest request, Album cartAlbum){
		   @SuppressWarnings("unchecked")
		   List<CartInfo> cartInfo = (List<CartInfo>) request.getSession().getAttribute("cartInfo");
		   for (int i = 0; i < cartInfo.size(); i++){
			   if(cartInfo.get(i).getAlbum().getID() == cartAlbum.getID()){
				   return i;
			   }
		   }
		   return -1;
	   }
	   
	   public void saveListCartInSession(HttpServletRequest request, List<CartInfo> newCart){
	       request.getSession().removeAttribute("cartInfo");
	       request.getSession().setAttribute("cartInfo", newCart);
	   }
	   
	   public int getTotalQuantity(HttpServletRequest request){
		List<CartInfo> cartInfo = getListCartInSession(request);
		   int totalQuantity = 0;
		   for (int i = 0; i < cartInfo.size(); i++){
			   totalQuantity += cartInfo.get(i).getQuantity();
		   }
		   return totalQuantity;
	   }
	   
	   public void setCheckOut(HttpServletRequest request, Boolean confirmCheckout) {
           request.getSession().setAttribute("confirmCheckout", confirmCheckout);
	   }
	   
	   public Boolean getCheckOut(HttpServletRequest request) {
		   Boolean confirmCheckout = false;
		   confirmCheckout = (Boolean) request.getSession().getAttribute("confirmCheckout");
		   return confirmCheckout;
	   }
	   
	   public double getTotalPrice(HttpServletRequest request) {
		   List<CartInfo> cartInfo = getListCartInSession(request);
		   double totalPrice = 0;
		   for (int i = 0; i < cartInfo.size(); i++){
			   totalPrice += cartInfo.get(i).getQuantity() * cartInfo.get(i).getUnitPrice();
		   }
		   return totalPrice;
	   }

	   public void addCart(String id, HttpServletRequest request){
		   List<CartInfo> listCart = cartSession.getListCartInSession(request);
		   Album album = null;
		   int albumID = Integer.parseInt(id);
		   album = albumDAO.findAlbum(albumID);
		   int pos = cartSession.isAdded(request, album);
		   if (pos > -1){//đã có trong cart
			   int quantity = listCart.get(pos).getQuantity();
			   listCart.get(pos).setQuantity(quantity + 1);
		   }
		   else{
			   int countCart = listCart.size();
			   String idCart = "0";
			   if (countCart > 0){
				   idCart = String.valueOf(Integer.parseInt(listCart.get(countCart - 1).getID()) + 1);
			   }

			   int quantity = 1;
			   double unitPrice = album.getPrice();
			   CartInfo cart = new CartInfo(idCart, album, quantity, unitPrice);
			   listCart.add(cart);
		   }
		   cartSession.saveListCartInSession(request, listCart);
	   }
}
