package com.pigmy.demospringboot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigmy.demospringboot.dao.GenreDAO;
import com.pigmy.demospringboot.entity.Album;
import com.pigmy.demospringboot.entity.Genre;
import com.pigmy.demospringboot.model.AlbumInfo;
import com.pigmy.demospringboot.model.GenreInfo;

//Transactional for Hibernate
@Repository
@Transactional
public class GenreDAOImpl implements GenreDAO {

	   @Autowired
	   private SessionFactory sessionFactory;
	   @Override
	   public List<GenreInfo> getListGenre() {
	   	List<GenreInfo> ds = new ArrayList<GenreInfo>();
	   	List<Genre> ds1 = new ArrayList<Genre>();
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Genre.class);
	       ds1 =crit.list();
	       for (Genre ds2:ds1)
	       {
	    	   GenreInfo ds3 = new GenreInfo(ds2.getID(),ds2.getName(),ds2.getDescription());
	       	ds.add(ds3);
	       }

	       return ds;
			 
	   }
	   @Override
	   public List<AlbumInfo> getListAlbumByGenre(int genreID){
		   String sql = "Select new " + AlbumInfo.class.getName() //
	                + "(ab.id, ab.genre, ab.artist , ab.title ,ab.price , ab.imageUrl) "//
	                + " from " + Album.class.getName() + " ab "//
	                + " where ab.genre.id = :genreID ";
	 
	        Session session = this.sessionFactory.getCurrentSession();
	 
	        Query query = session.createQuery(sql);
	        query.setParameter("genreID", genreID);
	 
	        return query.list();
	   }
	   
	   @Override
	   public Genre findGenre(int genreID) {
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Genre.class);
	       crit.add(Restrictions.eq("id", genreID));
	       return (Genre) crit.uniqueResult();
	   }
	   @Override
	   public GenreInfo findGenreDetail(int genreID){
		   Genre genre = this.findGenre(genreID);
	       if (genre == null) {
	           return null;
	       }
	       return new GenreInfo(genre.getID(), genre.getName(), genre.getDescription());

	   }
	   
	   @Override
	   public void save(GenreInfo genreInfo){
		   int genreID = genreInfo.getID();
		   Genre genre = null;
	       if (genreID > 0) {
	    	   genre = this.findGenre(genreID);
	       }
	       if (genre == null) {
	    	   genre = new Genre();
	       }
	       genre.setDescription(genreInfo.getDescription());
	       genre.setName(genreInfo.getName());
           this.sessionFactory.getCurrentSession().persist(genre);
	       // Ném ngoại lệ nếu có lỗi ở Db
	       this.sessionFactory.getCurrentSession().flush();
	   }

}
