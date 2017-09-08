-- Amazon Shortest Sub-segment Challenge
-- Given a list of words and a list of keywords, find the first sub-segment
-- with the shortest length that contains all the keywords. The length of
-- a sub-segment is the number of words it contains.

import Data.Char(ord, toUpper)

-- We represent a sub-segment as a pair of integers (a, b) where
-- a is the index of the first word in the sub-segment and
-- b is the index of the last word in the sub-segment.
-- Indices begins with 1.
type SubSegment = (Int,Int)

-- Given a list of words and a list of keywords, return the shortest
-- sub-segment containing all keywords if it exists.
firstShortestSubSegment :: [String] -> [String] -> Maybe SubSegment
firstShortestSubSegment wordList keywords =
    -- Create list of list of indices for each keywords.
    let keywordIndices = map (findIndicesForKeyword wordList) keywords
        -- Combine all index lists in to one list.
        indices = foldr merge [] keywordIndices
        -- The shortest sub-segment must begins with a keyword and
        -- end in a keyword.
        -- Find possible sub-segments grouped by the first index.
        possibleSubSegments =
            [subSegmentsStartingWith index indices | index <- indices]
        -- Eliminate sub-segments that do not contain all keywords.
        goodSubSegments = 
            map (findGoodSubSegment keywordIndices) possibleSubSegments
        -- find shortest good segment
    in case concat goodSubSegments of
        [] -> Nothing
        xs -> Just $ snd $ minimum $ zip (map segmentLen xs) xs

-- Find all indices of a keyword in a list of words.
findIndicesForKeyword :: [String] -> String -> [Int]
findIndicesForKeyword wordList keyword =
    [index | (index, w) <- zip [1..] wordList, eq w keyword]

-- Returns possible sub-segments starting with given first index.
subSegmentsStartingWith :: Int -> [Int] -> [SubSegment]
subSegmentsStartingWith firstIndex indices =
    [(firstIndex, lastIndex) | lastIndex <- dropWhile (<firstIndex) indices]

-- Returns singleton list of first sub-segment which contains all keywords,
-- if it exists.
findGoodSubSegment :: [[Int]] -> [SubSegment] -> [SubSegment]
findGoodSubSegment keyIndices subSegments =
    case dropWhile (tooShort keyIndices) subSegments of
        seg:_ -> [seg]
        _     -> []

-- Returns number of words of sub-segment.
segmentLen :: SubSegment -> Int
segmentLen (a,b) = b - a + 1

mkString :: [String] -> (Int,Int) -> String
mkString ws (a,b) = unwords $ take (b - a + 1) $ drop (a - 1) ws

isAtoZ :: Char -> Bool
isAtoZ c = (ordC >= (ord 'a') && ordC <= (ord 'z')) ||
           (ordC >= (ord 'A') && ordC <= (ord 'Z'))
           where ordC = (ord c)

-- Case-insensitive string equality
eq :: String -> String -> Bool
eq a b = (map toUpper a) == (map toUpper b)

ignoreNonLetters :: Char -> Char
ignoreNonLetters c
    | isAtoZ c  = c
    | otherwise = ' '

-- Returns True if subSegment does not contains a keyword.
uncovered :: SubSegment -> [Int] -> Bool
uncovered (a,b) ns = all (\n -> n < a || n > b) ns

-- Returns True if subSegment does not contains all keywords.
tooShort :: [[Int]] -> SubSegment -> Bool
tooShort keywordIndices segment = any (uncovered segment) keywordIndices

-- Merge two ordered lists of integers.
merge :: [Int] -> [Int] -> [Int]
merge [] ys = ys
merge xs [] = xs
merge xl@(x:xs) yl@(y:ys)
    | x < y     = x : merge xs yl
    | otherwise = y : merge xl ys

main = do
    (text:_:keywords) <- lines <$> readFile "input.txt"
    let wordList = words $ map ignoreNonLetters text
    let segment = firstShortestSubSegment wordList keywords
    let output = case segment of
                   Nothing     -> "NO SUBSEGMENT FOUND"
                   Just (a, b) -> unwords $ take (b-a+1) $ drop (a-1) wordList
    putStrLn $ output

