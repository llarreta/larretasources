package ar.com.larreta.persistence.dao.impl;

import ar.com.larreta.persistence.dao.args.LoadArguments;

public abstract class JoinedEntity extends QueryElement {
	
	private String joinType;
	private String alias;
	
	public JoinedEntity(LoadArguments args, String joinType, String property){
		this.args = args;
		this.joinType = joinType;
		this.name = property;
		this.alias = generateSymbol(args, property);
	}

	public String getJoinType() {
		return joinType;
	}
	public void setJoinType(String joinType) {
		this.joinType = joinType;
	}
	
	public String getHQL() {
		StringBuilder hql = new StringBuilder();
		hql.append(StandardDAOImpl.VOID).append(getJoinType()).append(StandardDAOImpl.VOID);
		String symbolicName = getSymbolicName();
		if (mainReference){
			hql.append(args.getMainEntity().getAlias()).append(StandardDAOImpl.DOT);
		}
		hql.append(symbolicName).append(StandardDAOImpl.VOID).append(alias).append(StandardDAOImpl.VOID);
		
		return hql.toString();
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof JoinedEntity) {
			JoinedEntity joinedEntity = (JoinedEntity) obj;
			return name.equals(joinedEntity.getName());
		}
		return Boolean.FALSE;
	}
	
}
