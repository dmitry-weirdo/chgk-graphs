<!DOCTYPE html>
<html lang="ru">

<head>
    <title>${tournament.name} &mdash; ${tournament.city} &mdash; графики</title>
    <script src="https://code.highcharts.com/highcharts.js"></script>
    <script src="https://code.highcharts.com/modules/exporting.js"></script>
    <script src="https://code.highcharts.com/modules/export-data.js"></script>
    <script src="https://code.highcharts.com/modules/accessibility.js"></script>

    <link rel="stylesheet" href="./style/main.css">
</head>

<body>
<h1>
    ${tournament.name} &mdash; ${tournament.city}
    <a href="${tournament.linkInOldRating}" target="_blank"><img src="./img/old-rating-logo.png" width="30px;" height="30px;" alt="Турнир в старом рейтинге"/></a>
    &nbsp;
    <a href="${tournament.linkInNewRating}" target="_blank"><img src="./img/new-rating.ico" width="30px;" height="30px;" alt="Турнир в рейтинге МАИИ"/></a>
    &nbsp;
    <a href="./index.html">Наверх</a>
</h1>

<figure class="highcharts-figure">
    <div id="container"></div>
    <!--
        <p class="highcharts-description">
            Сумма взятых вопросов после каждого вопроса.
        </p>
    -->
</figure>

<script>
    const teamsData = ${teamsData};

    const questionNumbers = [];
    for (let i = 1; i <= 72; i++) {
        questionNumbers.push(i)
    }

    const visibleTeamNames = ${visibleTeamNames};

    const tours = ${tours};

    const series = [];
    teamsData.forEach(function(item) {
        series.push({
            name: item.name,
            data: item.totalCorrectAnswers,
            visible: visibleTeamNames.includes(item.name) // hide most of the teams by default to be not super-slow
        });
    });

    let plotBands = [];
    tours.forEach(function(tour, index) {
        let color = (index % 2 === 0) ? 'rgba(200, 200, 200, 0.1)' : 'rgba(68, 170, 213, 0.1)';

        plotBands.push({
            from: tour.from - 1.5,
            to: tour.to - 0.5,
            color: color,
            label: {
                text: tour.editor,
                style: {
                    color: '#606060'
                }
            }
        })
    });

    Highcharts.chart('container', {
        chart: {
            type: 'line'
        },
        title: {
            text: 'Сумма взятых вопросов'
        },
        /*
                subtitle: {
                    text: 'Source: ' +
                        '<a href="https://en.wikipedia.org/wiki/List_of_cities_by_average_temperature" ' +
                        'target="_blank">Wikipedia.com</a>'
                },
        */
        xAxis: {
            categories: questionNumbers,
            plotBands: plotBands
        },
        yAxis: {
            title: {
                text: 'Взято вопросов'
            }
        },
        plotOptions: {
            line: {
                dataLabels: {
                    enabled: false
                },
                enableMouseTracking: true
            }
        },
        series: series
    });
</script>

</body>
</html>
