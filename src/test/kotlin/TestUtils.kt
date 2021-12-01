val String.test: String
	get() = "src/test/resources/$this.input"
val String.prod: String
	get() = "src/main/resources/$this.input"
