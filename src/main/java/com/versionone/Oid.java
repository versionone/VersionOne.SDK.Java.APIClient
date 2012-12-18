package com.versionone;

import com.versionone.apiclient.HashCode;
import com.versionone.apiclient.IAssetType;
import com.versionone.apiclient.IMetaModel;
import com.versionone.apiclient.OidException;

/**
 * VersionOne object identifier
 * @author jerry
 *
 */
public class Oid {
	protected static final String NullOidToken = "NULL";
	public static final Oid Null = new Oid();

	private IAssetType _type;
	private int _id;
	private Integer _moment;
    private static final String SEPARATOR = ":";

    /**
	 * Create NULL object identifier
	 */
	private Oid() {
	}

	/**
	 * Create fully qualified object identifier
	 * @param assetType
	 * @param id
	 * @param moment
     * @deprecated use {@link #Oid(com.versionone.apiclient.IAssetType, int, int)}
	 */
	public Oid(IAssetType assetType, DB.Int id, DB.Int moment) {
		if (assetType == null) {
			throw new IllegalArgumentException("assetType");
		} else if (id.isNull()) {
			throw new IllegalArgumentException("id cannot be DB.Null");
		} else if (moment == null) {
            moment = new DB.Int(DB.Null);
        }
		_type = assetType;
		_id = id.getValue();
		_moment = moment.getValue();
	}

	/**
	 * Create fully qualified object identifier
	 * @param assetType
	 * @param id
	 * @param moment
     * @deprecated use {@link #Oid(com.versionone.apiclient.IAssetType, int)}
	 */
	public Oid(IAssetType assetType, int id, DB.Int moment) {
		this(assetType, new DB.Int(id), moment);
	}

	/**
	 * Create fully qualified object identifier
	 * @param assetType
	 * @param id
	 * @param moment
	 */
	public Oid(IAssetType assetType, int id, int moment) {
		this(assetType, id);
        _moment = moment;
	}
	
	/**
	 * Create Object Identifier without moment
	 * @param assetType
	 * @param id
	 */
	public Oid(IAssetType assetType, int id) {
        if (assetType == null) {
            throw new IllegalArgumentException("assetType");
        }
        _type = assetType;
        _id = id;
	}

	/**
	 * Create Object Identifier with just an AssetType
	 * @param assetType
	 */
	public Oid(IAssetType assetType) {
		this(assetType, new DB.Int(0), new DB.Int(DB.Null));
	}

	/**
	 * Get the AssetType
	 * @return IAssetType for this OID
	 */
	public IAssetType getAssetType() {
		return _type;
	}

	/**
	 * Get the id for this instance
	 * @return value as Integer
	 */
	public Object getKey() {
		return _id;
	}

	/**
	 * get the moment for this oid 
	 * @return value as Integer or null
	 */
	public Object getMoment() {
		return _moment;
	}

	/**
	 * Is this OID null
	 * @return true if it's null, false otherwise
	 */
	public boolean isNull() {
		return this.equals(Null);
	}

    private String BuildToken(){
        if (_type == null) return NullOidToken;
        String typeToken = _type.getToken();
        StringBuilder res = new StringBuilder();
        res.append(typeToken).append(SEPARATOR).append(_id);
        if (hasMoment()) {
            res.append(SEPARATOR).append(_moment);
        }
        return res.toString();
    }


	/**
	 * Get the token for this object identifier
	 * @return string containing token
	 */
	public String getToken() {
        if (isNull()){
            return NullOidToken;
        }
        return BuildToken();
    }

	/**
	 * Object as string (the token)
	 */
	@Override
	public String toString() {
		return getToken();
	}

	/**
	 * Create an OID from a token
	 * @param oidtoken - token to parse
	 * @param meta - metamodel 
	 * @return an Oid
	 * @throws OidException - if the OID cannot be created.
	 */
    public static Oid fromToken(String oidtoken, IMetaModel meta) throws OidException {
        try {
            if (oidtoken.equals(NullOidToken)) {
                return Null;
            }
            String[] parts = oidtoken.split(SEPARATOR);
            IAssetType type = meta.getAssetType(parts[0]);
            int id = Integer.parseInt(parts[1]);
            if (parts.length > 2) {
                int moment = Integer.parseInt(parts[2]);
                return new com.versionone.Oid(type, id, moment);
            }
            return new com.versionone.Oid(type, id);
        } catch (Exception e) {
            throw new OidException("Invalid OID token", oidtoken, e);
        }
    }

    /**
	 * Get this instance as a momentless oid
	 * @return - momentless OID
	 */
	public Oid getMomentless() {
        return hasMoment() ? new Oid(_type, _id) : this;
    }

	/**
	 * Does this instance contain a moment?
	 * @return true if a moment exists, false otherwise
	 */
	public boolean hasMoment() {
		return _moment != null;
	}

    /**
     * Compare this instance to another oid
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Oid)) return false;

        Oid other = (Oid) obj;

        //are the AssetTypes equal?
        if (null == _type ^ null == other.getAssetType()) return false;
        if (null == _type && null == other.getAssetType()) return true;
        if (!_type.getToken().equals(other._type.getToken())) return false;

        //are the id's equal?
        if (_id != other._id) return false;

        //are the moments equal?
        if (hasMoment() ^ other.hasMoment()) return false;
        if (null == this.getMoment() && null == other.getMoment()) return true;
        if (!this.getMoment().equals(other.getMoment())) return false;

        return true;

    }

    /**
	 * Get the hash code for the oid
	 */
	@Override
	public int hashCode() {
        if (isNull()) {
            return 0;
        } else if (_moment == null) {
            return HashCode.Hash(_type.getToken().hashCode(), _id);
        }
        return HashCode.Hash(_type.getToken().hashCode(), _id, _moment.hashCode());
    }

	/**
	 * Compare two oids
	 * @param lhs - left hand side
	 * @param rhs - right hand side
	 * @return true if these are equal; false otherwise
	 */
	public static boolean compare(Oid lhs, Oid rhs) {
		if (lhs == null || rhs == null)
			return (lhs == rhs);
		return lhs.equals(rhs);
	}
}