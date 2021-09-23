import {useHistory, useParams} from "react-router-dom";
import {useEffect, useState} from "react";

const GameOver = () => {
    const { name, condition, answer } = useParams();
    const [ message, setMessage] = useState("");
    const history = useHistory();

    useEffect(() => {
        if (condition === "1") {
            setMessage("Congratulations! You win!");
        } else if (condition === "0"){
            setMessage("Game over! You lose!");
        }
    }, []);


    const handleClickNewGame = () => {
        history.push("/start");
    }

    const handleClickLeaderboard = () => {
        history.push("/leaderboard");
    }

    return (
        <div className="gameover">
            <h1>{name}</h1>
            <h1>{message}</h1>
            <h1>The answer was {answer}</h1>
            <button onClick={handleClickNewGame}>New Game</button>
            <button onClick={handleClickLeaderboard}>Leaderboard</button>
        </div>
    );
}

export default GameOver;