const editSeason = (function () {

    return {
        editRace: async function (raceId, seasonId) {

            var season = await api.getSeasonDto(seasonId);
            var drivers = season.drivers;
            var race = await api.getRaceDto(raceId);
            var track = race.track;
            api.editRaceResults(race, track, drivers);
        }
    };
})();