<!DOCTYPE html>
<html lang="ru">

<head>
    <title>Графики турниров ЧГК</title>
    <link rel="stylesheet" href="./style/main.css">
</head>

<body>
<h1>Графики турниров ЧГК</h1>

<ul>
    <#list generators as generator>
        <li><a href="./${generator.htmlFileName}">${generator.tournament.name} &mdash; ${generator.tournament.city}</a></li>
    </#list>
</ul>

</body>
</html>
