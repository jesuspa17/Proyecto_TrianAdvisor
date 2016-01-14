package com.dam.salesianostriana.proyecto_trianadvisor.greendao;

import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "VALORACION".
 */
public class Valoracion {

    private Long id;
    private String objectId;
    private String valoracion;
    private String updatedAt;
    private Long sitioId_v;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient ValoracionDao myDao;

    private Sitio sitio;
    private Long sitio__resolvedKey;


    public Valoracion() {
    }

    public Valoracion(Long id) {
        this.id = id;
    }

    public Valoracion(Long id, String objectId, String valoracion, String updatedAt, Long sitioId_v) {
        this.id = id;
        this.objectId = objectId;
        this.valoracion = valoracion;
        this.updatedAt = updatedAt;
        this.sitioId_v = sitioId_v;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getValoracionDao() : null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Long getSitioId_v() {
        return sitioId_v;
    }

    public void setSitioId_v(Long sitioId_v) {
        this.sitioId_v = sitioId_v;
    }

    /** To-one relationship, resolved on first access. */
    public Sitio getSitio() {
        Long __key = this.sitioId_v;
        if (sitio__resolvedKey == null || !sitio__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            SitioDao targetDao = daoSession.getSitioDao();
            Sitio sitioNew = targetDao.load(__key);
            synchronized (this) {
                sitio = sitioNew;
            	sitio__resolvedKey = __key;
            }
        }
        return sitio;
    }

    public void setSitio(Sitio sitio) {
        synchronized (this) {
            this.sitio = sitio;
            sitioId_v = sitio == null ? null : sitio.getId();
            sitio__resolvedKey = sitioId_v;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
