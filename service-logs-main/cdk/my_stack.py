from aws_cdk import core
from aws_cdk import aws_lambda as _lambda
from aws_cdk import aws_sqs as sqs
from aws_cdk import aws_lambda_event_sources as lambda_event_sources

class MyStack(core.Stack):

    def __init__(self, scope: core.Construct, id: str, **kwargs) -> None:
        super().__init__(scope, id, **kwargs)

        # Create an SQS Queue
        my_queue = sqs.Queue(self, 'MyQueue')

        # Create a Lambda function
        my_lambda = _lambda.Function(self, 'MyLambda',
            runtime=_lambda.Runtime.PYTHON_3_7,
            handler='index.handler',
            code=_lambda.Code.from_asset('my-lambda-code'),
        )

        # Add the SQS queue as a trigger for the Lambda function
        my_lambda.add_event_source(lambda_event_sources.SqsEventSource(my_queue))
