import {useHistory} from "react-router-dom";

const Home = () => {
    const history = useHistory();

    const handleClickStartGame = () => {
        history.push("/start");
    }

    const handleClickLeaderboard = () => {
        history.push("/leaderboard");
    }
    
    return (
        <div className="Home">
            <h1>Guessing game</h1>
            <p>Game rules:</p>
            <ul>
                <div style={{width: "60%",padding: "0% 37%" , textAlign: "left"}}>
                <li>Program chooses a random secret number with 4 digits.</li>
                <li>All digits in the secret number are different.</li>
                <li>Player has 8 tries to guess the secret number.</li>
                <li>After each guess program displays the message "M:m; P:p" where:</li>
                <ul>
                    <li>m - number of matching digits but not on the right places</li>
                    <li>p - number of matching digits on exact places</li>
                </ul>
                <li>Game ends after 8 tries or if the correct number is guessed.</li>
                </div>
            </ul>
                <button onClick={handleClickStartGame}>Start Game!</button>
                <button onClick={handleClickLeaderboard}>Leaderboard</button>
        </div>
    );
}

export default Home;