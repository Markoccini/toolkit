package gg.jte.generated.ondemand.polls;
@SuppressWarnings("unchecked")
public final class JteindexGenerated {
	public static final String JTE_NAME = "polls/index.jte";
	public static final int[] JTE_LINE_INFO = {0,0,0,0,0,22,22,22,24,24,24,25,25,25,27,27,32,32,32,0,0,0,0};
	public static void render(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.List<org.markoccini.toolkit.poll.model.Poll> polls) {
		jteOutput.writeContent("\n<!doctype html>\n<html lang=\"en\">\n<head>\n    <meta charset=\"UTF-8\">\n    <meta name=\"viewport\"\n          content=\"width=device-width, user-scalable=no, initial_scale=1.0, maximum-scale=1.0, minimum-scale=1.0\">\n    <meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\">\n    <title>MY APP</title>\n</head>\n<body>\n    <h1>Hello! </h1>\n\n<table>\n    <thead>\n    <tr>\n        <th>ID</th>\n        <th>Question</th>\n    </tr>\n    </thead>\n    <tbody>\n        ");
		for ( org.markoccini.toolkit.poll.model.Poll poll : polls) {
			jteOutput.writeContent("\n            <tr>\n                <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(poll.getId());
			jteOutput.writeContent("</td>\n                <td>");
			jteOutput.setContext("td", null);
			jteOutput.writeUserContent(poll.getQuestion());
			jteOutput.writeContent("</td>\n            </tr>\n        ");
		}
		jteOutput.writeContent("\n    </tbody>\n</table>\n\n</body>\n</html>");
	}
	public static void renderMap(gg.jte.html.HtmlTemplateOutput jteOutput, gg.jte.html.HtmlInterceptor jteHtmlInterceptor, java.util.Map<String, Object> params) {
		java.util.List<org.markoccini.toolkit.poll.model.Poll> polls = (java.util.List<org.markoccini.toolkit.poll.model.Poll>)params.get("polls");
		render(jteOutput, jteHtmlInterceptor, polls);
	}
}
