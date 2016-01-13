package com.dam.salesianostriana.proyecto_trianadvisor.greendao;

import java.util.List;
import com.dam.salesianostriana.proyecto_trianadvisor.greendao.DaoSession;
import de.greenrobot.dao.DaoException;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "USUARIO".
 */
public class Usuario {

    private Long id;
    private String objectId;
    private String nombre;
    private String email;
    private String username;
    private String url_foto;
    private String sessionToken;
    private String updatedAt;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient UsuarioDao myDao;

    private List<Valoracion> Valoraciones_sitio;
    private List<Comentario> Comentario_usuario;

    public Usuario() {
    }

    public Usuario(Long id) {
        this.id = id;
    }

    public Usuario(Long id, String objectId, String nombre, String email, String username, String url_foto, String sessionToken, String updatedAt) {
        this.id = id;
        this.objectId = objectId;
        this.nombre = nombre;
        this.email = email;
        this.username = username;
        this.url_foto = url_foto;
        this.sessionToken = sessionToken;
        this.updatedAt = updatedAt;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUsuarioDao() : null;
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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUrl_foto() {
        return url_foto;
    }

    public void setUrl_foto(String url_foto) {
        this.url_foto = url_foto;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Valoracion> getValoraciones_sitio() {
        if (Valoraciones_sitio == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ValoracionDao targetDao = daoSession.getValoracionDao();
            List<Valoracion> Valoraciones_sitioNew = targetDao._queryUsuario_Valoraciones_sitio(id);
            synchronized (this) {
                if(Valoraciones_sitio == null) {
                    Valoraciones_sitio = Valoraciones_sitioNew;
                }
            }
        }
        return Valoraciones_sitio;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetValoraciones_sitio() {
        Valoraciones_sitio = null;
    }

    /** To-many relationship, resolved on first access (and after reset). Changes to to-many relations are not persisted, make changes to the target entity. */
    public List<Comentario> getComentario_usuario() {
        if (Comentario_usuario == null) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            ComentarioDao targetDao = daoSession.getComentarioDao();
            List<Comentario> Comentario_usuarioNew = targetDao._queryUsuario_Comentario_usuario(id);
            synchronized (this) {
                if(Comentario_usuario == null) {
                    Comentario_usuario = Comentario_usuarioNew;
                }
            }
        }
        return Comentario_usuario;
    }

    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    public synchronized void resetComentario_usuario() {
        Comentario_usuario = null;
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
