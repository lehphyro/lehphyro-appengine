package com.google.code.luar.syntax.tree;

import java.util.*;

public class TableConstructorNode {

	private List<FieldExpression> fields;
	
	public TableConstructorNode() {
		fields = new ArrayList<FieldExpression>();
	}
	
	public List<FieldExpression> getFields() {
		return Collections.unmodifiableList(fields);
	}
	
	public void addField(FieldExpression field) {
		fields.add(field);
	}
	
	class FieldExpression {
		
		private ExpressionNode key;
		
		private ExpressionNode value;

		public ExpressionNode getKey() {
			return key;
		}

		public void setKey(ExpressionNode key) {
			this.key = key;
		}

		public ExpressionNode getValue() {
			return value;
		}

		public void setValue(ExpressionNode value) {
			this.value = value;
		}
	}
}