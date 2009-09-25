package com.lehphyro.btracs;

import java.io.*;

public class WebSite implements Serializable, Comparable<WebSite> {

	private static final long serialVersionUID = 1193197669369442544L;
	
	private String name;
	private String url;
	private String category;
	private String language;
	private Integer ranking;

	public static class Builder {
		private String name;
		private String url;
		private String category;
		private String language;
		private Integer ranking;
		
		public Builder(String name, String url) {
			this.name = name;
			this.url = url;
		}
		
		public Builder category(String value) {
			this.category = value; return this;
		}

		public Builder language(String value) {
			this.language = value; return this;
		}

		public Builder ranking(Integer value) {
			this.ranking = value; return this;
		}
		
		public WebSite build() {
			return new WebSite(this);
		}
	}
	
	private WebSite(Builder builder) {
		this.name = builder.name;
		this.url = builder.url;
		this.category = builder.category;
		this.language = builder.language;
		this.ranking = builder.ranking;
	}
	
	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	public String getCategory() {
		return category;
	}

	public String getLanguage() {
		return language;
	}

	public Integer getRanking() {
		return ranking;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((category == null) ? 0 : category.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((ranking == null) ? 0 : ranking.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof WebSite)) {
			return false;
		}
		WebSite other = (WebSite) obj;
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (language == null) {
			if (other.language != null) {
				return false;
			}
		} else if (!language.equals(other.language)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (ranking == null) {
			if (other.ranking != null) {
				return false;
			}
		} else if (!ranking.equals(other.ranking)) {
			return false;
		}
		if (url == null) {
			if (other.url != null) {
				return false;
			}
		} else if (!url.equals(other.url)) {
			return false;
		}
		return true;
	}
	
	@Override
	public int compareTo(WebSite other) {
		if (getRanking() == null) {
			return -1;
		}
		if (other.getRanking() == null) {
			return 1;
		}
		return getRanking() - other.getRanking();
	}

	@Override
	public String toString() {
		return String.format("WebSite [name=%s, url=%s, category=%s, language=%s, ranking=%s]", name, url, category, language, ranking);
	}

}
