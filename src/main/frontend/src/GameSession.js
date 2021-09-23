import { useState } from "react";
import { useParams, useHistory } from "react-router-dom";
import Turns from './Turns';

const GameSession = () => {
    const { name, sessionId, maxTries } = useParams();
    const [digit1, setDigit1] = useState("");
    const [digit2, setDigit2] = useState("");
    const [digit3, setDigit3] = useState("");
    const [digit4, setDigit4] = useState("");
    const [tries, setTries] = useState([]);
    const [triesLeft, setTriesLeft] = useState(maxTries);
    const [errorMessage, setErrorMessage] = useState("");
    const [guesses, setGuesses] = useState([]);
    const history = useHistory();

    const handleSubmit = (e) => {
        e.preventDefault();
        let guess = [ digit1, digit2, digit3, digit4 ];
        guess = guess.join("");
        setGuesses(guesses => [...guesses, guess]);
        //console.log(guessOutput);

        fetch("http://localhost:8080/game", {
            method: "POST",
            headers: { "Content-Type": "application/json"},
            body: JSON.stringify({sessionId: sessionId, guess: guess})
        }).then(response => response.text())
          .then((data) => {
            const playerResponse = JSON.parse(data);

            if(playerResponse.status === 1) {
                setErrorMessage("");
                setTriesLeft(playerResponse.triesLeft);
                const condition = playerResponse.win;
                if (condition === -1) {
                    const turn = [playerResponse.m, playerResponse.p];
                    setTries(turns => [...turns, turn]);
                } else {
                    history.push(`/gameover/${name}/${condition}/${playerResponse.answer}`);
                }
            } else if (playerResponse.status === 0) {
                setErrorMessage("Input was incorrect!");
            }
        });

        setDigit1("");
        setDigit2("");
        setDigit3("");
        setDigit4("");
    }

    return (
        <div className="Start">
            <h1>{name}</h1>
            <form onSubmit={handleSubmit}>
                <label>Digit 1:</label>
                <input
                  type="text"
                  pattern="[0-9]"
                  maxLength={1}
                  required
                  value={digit1}
                  onChange={ (e) => setDigit1(e.target.value) }
                />
                <label>Digit 2:</label>
                <input
                  type="text"
                  pattern="[0-9]"
                  maxLength={1}
                  required
                  value={digit2}
                  onChange={ (e) => setDigit2(e.target.value) }
                />
                <label>Digit 3:</label>
                <input
                  type="text"
                  pattern="[0-9]"
                  maxLength={1}
                  required
                  value={digit3}
                  onChange={ (e) => setDigit3(e.target.value) }
                />
                <label>Digit 4:</label>
                <input
                  type="text"
                  pattern="[0-9]"
                  maxLength={1}
                  required
                  value={digit4}
                  onChange={ (e) => setDigit4(e.target.value) }
                />
                <button>Make Guess</button>
            </form>
            <h1>{errorMessage}</h1>
            <Turns turns={tries} guessOutput={guesses}/>
            <p>Tries left: {triesLeft}</p>
        </div>
    )
}

export default GameSession;