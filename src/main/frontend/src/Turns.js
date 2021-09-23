const Turns = ({turns, guessOutput}) => {

    return (
        <div className="tries">
            {turns.map((message, index) => (
                    <p key={index}>#{index+1} --  {guessOutput[index]}  /  M: {message[0]}; P: {message[1]}</p>
            ))}
        </div>
    );
}

export default Turns;