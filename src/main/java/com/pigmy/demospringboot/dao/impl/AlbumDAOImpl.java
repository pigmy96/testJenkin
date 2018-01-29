package com.pigmy.demospringboot.dao.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigmy.demospringboot.dao.AlbumDAO;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.model.AlbumInfo;
@Repository
@Transactional
public class AlbumDAOImpl implements AlbumDAO{
	@Autowired
	   private SessionFactory sessionFactory;
	   @Override
	   public List<AlbumInfo> getListAlbum() {
	   	List<AlbumInfo> ds = new ArrayList<AlbumInfo>();
	   	List<Album> ds1 = new ArrayList<Album>();
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Album.class);
	       ds1 =crit.list();
	       for (Album ds2:ds1)
	       {
	    	   AlbumInfo ds3 = new AlbumInfo(ds2.getID(),ds2.getGenre(), ds2.getArtist(), ds2.getTitle(),
	    			   ds2.getPrice(), ds2.getImageUrl());
	       	ds.add(ds3);
	       }

	       return ds;
			 
	   }
	   @Override
	   public Album findAlbum(int albumID) {
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Album.class);
	       crit.add(Restrictions.eq("id", albumID));
	       return (Album) crit.uniqueResult();
	   }
	   @Override
	   public AlbumInfo findAlbumDetail(int albumID){
		   Album album = this.findAlbum(albumID);
	       if (album == null) {
	           return null;
	       }
	       System.out.println(album.getTitle());
	       return new AlbumInfo(album.getID(), album.getGenre(), album.getArtist(), album.getTitle(),
	    		   album.getPrice(), album.getImageUrl());

	   }
	   
	   @Override
	   public void save(AlbumInfo albumInfo) {
		   int albumID = albumInfo.getID();
	       Album album = null;
	       System.out.println("save album");
	       if (albumID > 0) {
	    	   album = this.findAlbum(albumID);
	       }
	       if (album == null) {
	           album = new Album();
	       }
	       album.setTitle(albumInfo.getTitle());
	       album.setGenre(albumInfo.getGenre());
	       album.setArtist(albumInfo.getArtist());
	       album.setPrice(albumInfo.getPrice());
	       if (albumInfo.getImageUrl() == null && album.getImageUrl() == null){
	    	   album.setImageUrl("albumdefault.jpg");
	       }
	       else{
	    	   if (albumInfo.getImageUrl() != null){
	    	   	if(album.getImageUrl() != null && album.getImageUrl().equals("albumdefault.jpg") == false){
					String name = "images" + File.separator + album.getImageUrl();
					File file = new File(name);
					file.delete();
				}
			       album.setImageUrl(albumInfo.getImageUrl());
	    	   }
	    	   
	       }
           this.sessionFactory.getCurrentSession().persist(album);
	       // Ném ngoại lệ nếu có lỗi ở Db
	       this.sessionFactory.getCurrentSession().flush();
	   }
	   
	   @Override
	   public void delete(AlbumInfo albumInfo) {
	       int id = albumInfo.getID();
	       
	       Album album = null;
		   album = this.findAlbum(id);
		   if(album.getImageUrl() != null && album.getImageUrl().equals("albumdefault.jpg") == false){
			   String name = "images" + File.separator + album.getImageUrl();
			   File file = new File(name);
			   file.delete();
		   }
	       Session session = sessionFactory.getCurrentSession();
	       session.delete(album);
	       session.flush();
	   }
	   @Override
	   public Album getAlbumLast(){
		   List<Album> ds1 = new ArrayList<Album>();
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Album.class);
	       ds1 =crit.list();

	       return ds1.get(ds1.size() - 1);
	   }
}
