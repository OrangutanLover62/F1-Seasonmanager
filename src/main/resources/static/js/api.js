const api = (function () {

    function get(endpoint) {
        return $.get({
            url: '/api' + endpoint
        });
    }

    function post(endpoint, payload) {
        return $.post({
            url: '/api' + endpoint,
            data: JSON.stringify(payload),
            contentType: 'application/json; charset=utf-8'
        });
    }

    async function syncpost(endpoint, payload){
        var res;
        await $.ajax({
          url: '/api' + endpoint,
          type: 'POST',
          contentType: 'application/json',
          data: JSON.stringify(payload),
          async: false, // Set async to false for synchronous request
          success: function(response) {
            // Request was successful
            res = response;
          },
          error: function(xhr, status, error) {
            // Handle errors
            console.error('Error:', status, error);
          }
        });
        return res;
    }


    return {
        createDriver: function (newDriver) {
            let payload = {
                firstname: newDriver.find('#firstname').val(),
                lastname: newDriver.find('#lastname').val(),
                imageBase64: newDriver.find('#imageBase64').val()
            };
            return post('/driver/create', payload);
        },
        htmlByDriverName: function (name) {
            return get('/driver/find/html?name=' + name);
        },
        htmlByDriverId: function (id) {
            return get('/driver/find/html?id=' + id);
        },
        htmlAllTracks: function () {
            return get('/track/allTracks');
        },
        htmlSelectedTrack: function () {
            return get('/track/selectedTrack');
        },
        htmlRaceCreator: function (track) {
            let payload = {
                id: track.id,
                name: track.name,
                imageBase64: track.imageBase64
            };
            return post('/race/creationEditor', payload);
        },
        getDriverDto: function (id) {
            return syncpost('/driver/findById?id=' + id);
        },
        postRaceDto: function (id, laps, track) {
            return syncpost('/race/generateById?id=' + id + '&laps=' + laps, track);
        },
        getRaceDto: function (id) {
            return get('/race/findById?id=' + id);
        },
        getTrackDto: function (id) {
            return get('/season/byId?id=' + id);
        },
        getSeasonDto: function (id) {
                    return get('/season/byId?id=' + id);
                },
        createSeason: function (seasonName, races, drivers) {
            let payload = {
                name: seasonName,
                races: races,
                drivers: drivers
            };

            return post('/season/create', payload);
        },
        editRaceResults: function (race, track, drivers) {
            let payload = {
                race: race,
                track: track,
                drivers: drivers
            };
            console.log(race);
            console.log(track);
            console.log(drivers);
            return post('/edit/race', payload);
        }
    };
})();
