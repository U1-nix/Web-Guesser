import {useEffect, useState} from "react";
import {useHistory} from "react-router-dom";
import LeaderboardOutput from "./LeaderboardOutput";

const Leaderboard = () => {
    const [ playerList, setPlayerList] = useState([]);
    const [ minimumGamesPlayed, setMinimumGamesPlayed] = useState("1");
    const [ errorMessage, setErrorMessage ] = useState("");
    const [ emptyLeaderboard, setEmptyLeaderboard ] = useState("");
    const history = useHistory();

    useEffect( () => {
        fetch("http://localhost:8080/leaderboard", {
            method: "POST",
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({minimumGamesPlayed: minimumGamesPlayed})
        }).then(response => response.text())
            .then((data) => {
                const leaderboard = JSON.parse(data);
                updateLeaderboard(leaderboard);
            });
    }, []);

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch("http://localhost:8080/leaderboard", {
            method: "POST",
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({minimumGamesPlayed: minimumGamesPlayed})
        }).then(response => response.text())
            .then((data) => {
                const leaderboard = JSON.parse(data);
                updateLeaderboard(leaderboard);
            });
    }

    const updateLeaderboard = (leaderboard) => {
        if (leaderboard.status === 1) {
            if (leaderboard.playerList.length === 0) {
                setEmptyLeaderboard("There are no players with so many games");
            } else {
                setEmptyLeaderboard("");
            }
            setErrorMessage("");
            setPlayerList(leaderboard.playerList);
        } else if (leaderboard.status === 0) {
            setErrorMessage("Something went wrong");
        }
    }

    const handleClickStartNewGame = () => {
        history.push("/start");
    }

    return (
        <div className="Leaderboard">
            <h1>Leaderboard</h1>
            <h1>{errorMessage}</h1>
            <LeaderboardOutput playerList={playerList} />
            <h1>{ emptyLeaderboard }</h1>
            <form onSubmit={handleSubmit}>
                <label>
                    Input minimum games played:<br/>
                    <input
                        type="number"
                        value={minimumGamesPlayed}
                        onChange={ (e) => setMinimumGamesPlayed(e.target.value) }
                    /><br/>
                    Players will be included in the leaderboard if at least {minimumGamesPlayed} games are played<br/>
                </label>
                <input type="submit" value="Show Leaderboard" />
            </form>
            <button onClick={handleClickStartNewGame}>Start New Game</button>
        </div>
    );
}

export default Leaderboard;