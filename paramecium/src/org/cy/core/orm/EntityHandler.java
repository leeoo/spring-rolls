package org.cy.core.orm;

import java.util.HashMap;
import java.util.Map;

import org.cy.core.orm.annotation.PrimaryKey.AUTO_GENERATE_MODE;

public class EntityHandler {
	public String[] primaryKeys;
	public AUTO_GENERATE_MODE mode = AUTO_GENERATE_MODE.NATIVE;
	public final Map<String, String> entityMappingTable = new HashMap<String, String>();
}
