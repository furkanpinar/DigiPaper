package com.digipaper.framework.query;

import com.digipaper.framework.enums.ClauseType;
import com.digipaper.framework.generics.GenericModel;
import com.digipaper.framework.interfaces.IGenericModel;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

@Stateless
@Dependent
public class QueryService<T extends GenericModel> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    protected EntityManager em;

    public T getSingle(String queryRoot, List<QueryParam> where, String rest) {
        em.joinTransaction();

        List<T> objects = get(queryRoot, where, rest, 1);
        if (!objects.isEmpty()) {
            return objects.get(0);
        } else {
            return null;
        }
    }

    public List<T> get(String queryRoot, List<QueryParam> where, String restriction, Integer limit) {
        em.joinTransaction();

        String whereClause = "";

        if (where != null) {
            queryRoot += " where 1 = 1";
            for (QueryParam queryParam : where) {
                String key = queryParam.getKey();
                Object value = queryParam.getValue();
                ClauseType clauseType = queryParam.getType();

                if (ClauseType.EQUAL.equals(clauseType) || ClauseType.EQUALFORCODE.equals(clauseType)) {
                    if (value instanceof GenericModel) {
                        value = ((GenericModel) value).getId();
                    } else if (value instanceof String) {
                        value = "'" + value + "'";
                    }
                    whereClause += " and " + key + " = " + value;
                } else if (ClauseType.LITERAL.equals(clauseType)) {
                    whereClause += " " + value;
                } else if (ClauseType.LITERALEQUAL.equals(clauseType)) {
                    whereClause += " and " + key + " = " + value;
                } else if (ClauseType.NOTEQUAL.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.LIKE.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.LIKE2.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.BETWEEN.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.ISNULL.equals(clauseType)) {
                    whereClause += " and " + key + " is null";
                } else if (ClauseType.GREATERTHANOREQUAL.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.GREATERTHAN.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.LESSTHANOREQUALTO.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.LESSTHAN.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                } else if (ClauseType.ISNOTNULL.equals(clauseType)) {
                    whereClause += " and " + key + " is not null";
                } else if (ClauseType.IN.equals(clauseType)) {
                    throw new EJBException("Not yet implemented");
                }
            }
        }

        if (restriction == null) {
            restriction = "";
        }

        Query query = em.createQuery(queryRoot + whereClause + " " + restriction);
        if (limit != null) {
            query.setMaxResults(limit);
        }

        List<T> list = query.getResultList();
        return list;
    }

    public IGenericModel find(Class cls, IGenericModel iGenericModel) {
        em.joinTransaction();
        return (IGenericModel) em.find(cls, iGenericModel.getId());
    }

    public IGenericModel find(Class cls, Integer id){
        em.joinTransaction();
        return (IGenericModel) em.find(cls, id);
    }

    public void delete(IGenericModel iGenericModel) {
        em.joinTransaction();
        em.remove(iGenericModel);
    }

    public void update(String query) {
        em.joinTransaction();
        em.createQuery(query).executeUpdate();
    }
}
