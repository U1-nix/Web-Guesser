import { useHistory } from "react-router-dom";
import { useState } from "react";

const Start = () => {
    const [playerName, setPlayerName] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    const history = useHistory();

    const handleSubmit = (e) => {
        e.preventDefault();
        fetch("http://localhost:8080/start", {
            method: "POST",
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({playerName: playerName})
        }).then(response => response.text())
            .then((data) => {
                const initPlayerData = JSON.parse(data);
                if (initPlayerData.status === 1) {
                    history.push(`/game/${playerName}/${initPlayerData.sessionId}/${initPlayerData.triesLeft}`);
                } else if (initPlayerData.status === 0){
                    setErrorMessage("Something went wrong");
                }
            });
    }

    return (
        <div className="Start">
            <h1>Enter your name!</h1>
            <h1>{errorMessage}</h1>

            <form onSubmit={handleSubmit}>
                <label>Your name:</label>
                <input
                  type="text"
                  required
                  value={playerName}
                  onChange={ (e) => setPlayerName(e.target.value) }
                />
                <button>Start</button>
            </form>
        </div>
    );
}

export default Start;