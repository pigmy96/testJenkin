package com.pigmy.demospringboot.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pigmy.demospringboot.dao.ArtistDAO;
import com.pigmy.demospringboot.entity.Artist;
import com.pigmy.demospringboot.model.ArtistInfo;
@Repository
@Transactional
public class ArtistDAOImpl implements ArtistDAO{

	   @Autowired
	   private SessionFactory sessionFactory;
	   @Override
	   public List<ArtistInfo> getListArtist() {
	   	List<ArtistInfo> ds = new ArrayList<ArtistInfo>();
	   	List<Artist> ds1 = new ArrayList<Artist>();
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Artist.class);
	       ds1 =crit.list();
	       for (Artist ds2:ds1)
	       {
	    	   ArtistInfo ds3 = new ArtistInfo(ds2.getID(),ds2.getName());
	       	ds.add(ds3);
	       }

	       return ds;
			 
	   }
	   @Override
	   public Artist findArtist(int id) {
	       Session session = sessionFactory.getCurrentSession();
	       Criteria crit = session.createCriteria(Artist.class);
	       crit.add(Restrictions.eq("id", id));
	       return (Artist) crit.uniqueResult();
	   }
	   @Override
	   public ArtistInfo findArtistDetail(int artistID){
		   Artist artist = this.findArtist(artistID);
	       if (artist == null) {
	           return null;
	       }
	       return new ArtistInfo(artist.getID(), artist.getName());

	   }
	   @Override
	   public void save(ArtistInfo artistInfo){
		   int artistID = artistInfo.getID();
		   Artist artist = null;
	       if (artistID > 0) {
	    	   artist = this.findArtist(artistID);
	       }
	       if (artist == null) {
	    	   artist = new Artist();
	       }
	       artist.setName(artistInfo.getName());
           this.sessionFactory.getCurrentSession().persist(artist);
	       this.sessionFactory.getCurrentSession().flush();
	   }
}
