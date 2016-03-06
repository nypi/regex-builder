package io.neuropop.util.regex;

import java.util.Objects;

// package-private
abstract class LogicalExpression implements Expression {
		
	/* and */
	
	static class And extends LogicalExpression {
		private final Expression x;
		private final Expression y;
		
		public And(Expression x, Expression y) {
			Objects.requireNonNull(x);
			Objects.requireNonNull(y);

			this.x = x;
			this.y = y;
		}

		@Override
		public String regex() {
			StringBuilder builder = new StringBuilder();
			if (x instanceof Or)
				builder.append("(?:").append(x.regex()).append(")");
			else
				builder.append(x.regex());
			if (y instanceof Or)
				builder.append("(?:").append(y.regex()).append(")");
			else
				builder.append(y.regex());
			return builder.toString();
		}
	}
	
	/* or */
	
	public static class Or extends LogicalExpression {
		private final Expression x;
		private final Expression y;
		
		public Or(Expression x, Expression y) {
			Objects.requireNonNull(x);
			Objects.requireNonNull(y);

			this.x = x;
			this.y = y;
		}

		@Override
		public String regex() {
			return new StringBuilder()
					.append(x.regex())
					.append("|")
					.append(y.regex())
					.toString();
		}
	}
}
