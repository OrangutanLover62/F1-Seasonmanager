const editSeason = (function () {

    return {
        editRace: async function (raceId, seasonId) {

            var season = await api.getSeasonDto(seasonId);
            var drivers = season.drivers;
            var race = await api.getRaceDto(raceId);
            var track = race.track;
            console.log(race);
            console.log(track);
            //api.editRaceResults(race, track, drivers);
        }
    };
})(); // Invoke the function to create an instance of editSeason
