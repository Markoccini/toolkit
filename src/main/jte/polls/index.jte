@param java.util.List<org.markoccini.toolkit.poll.model.Poll> polls

<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial_scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>MY APP</title>
</head>
<body>
    <h1>Hello! </h1>

<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>Question</th>
    </tr>
    </thead>
    <tbody>
        @for ( org.markoccini.toolkit.poll.model.Poll poll : polls)
            <tr>
                <td>${poll.getQuestion()}</td>
            </tr>
        @endfor
    </tbody>
</table>

</body>
</html>