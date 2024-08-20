cd $PSScriptRoot
Write-Host "Creating Lexer"
./lexer/make-lexer

cd $PSScriptRoot
Write-Host "Creating Parser"
./parser/make-parser
